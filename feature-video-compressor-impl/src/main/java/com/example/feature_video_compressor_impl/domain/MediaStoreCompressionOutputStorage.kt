package com.example.feature_video_compressor_impl.domain

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MediaStoreCompressionOutputStorage @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : CompressionOutputStorage {

    override fun newTempFile(): File {
        val directory = File(
            context.cacheDir,
            TEMP_DIRECTORY_NAME,
        )

        if (!directory.exists() && !directory.mkdirs()) {
            throw IOException(
                "Failed to create compression temp directory",
            )
        }

        return File(
            directory,
            buildFileName(),
        )
    }

    override suspend fun publish(
        tempFile: File,
    ) = withContext(Dispatchers.IO) {
        val resolver = context.contentResolver

        val values = ContentValues().apply {
            put(
                MediaStore.Video.Media.DISPLAY_NAME,
                buildFileName(),
            )

            put(
                MediaStore.Video.Media.MIME_TYPE,
                VIDEO_MIME_TYPE,
            )

            put(
                MediaStore.Video.Media.RELATIVE_PATH,
                OUTPUT_RELATIVE_PATH,
            )

            put(
                MediaStore.Video.Media.IS_PENDING,
                1,
            )
        }

        val outputUri = resolver.insert(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            values,
        ) ?: throw IOException(
            "Failed to create MediaStore video",
        )

        try {
            val outputStream =
                resolver.openOutputStream(outputUri)
                    ?: throw IOException(
                        "Failed to open MediaStore output stream",
                    )

            outputStream.use { output ->
                tempFile.inputStream().use { input ->
                    input.copyTo(output)
                }
            }

            val publishValues = ContentValues().apply {
                put(
                    MediaStore.Video.Media.IS_PENDING,
                    0,
                )
            }

            val updatedRows = resolver.update(
                outputUri,
                publishValues,
                null,
                null,
            )

            if (updatedRows == 0) {
                throw IOException(
                    "Failed to publish MediaStore video",
                )
            }

            if (!tempFile.delete()) {
                tempFile.deleteOnExit()
            }

            outputUri
        } catch (throwable: Throwable) {
            resolver.delete(
                outputUri,
                null,
                null,
            )

            throw throwable
        }
    }

    private fun buildFileName(): String {
        return "VideoCompressor_${System.currentTimeMillis()}.mp4"
    }

    private companion object {

        const val TEMP_DIRECTORY_NAME =
            "video_compression"

        const val VIDEO_MIME_TYPE =
            "video/mp4"

        val OUTPUT_RELATIVE_PATH = "${Environment.DIRECTORY_MOVIES}/Video Compressor"
    }
}