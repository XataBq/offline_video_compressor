package com.example.feature_video_compressor_impl.domain

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.example.feature_video_compressor_api.usecase.CompressedVideoSaver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MediaStoreCompressedVideoSaver @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : CompressedVideoSaver {

    override fun save(
        sourceFile: File,
        displayName: String,
    ): Uri {
        val resolver = context.contentResolver

        val values = ContentValues().apply {
            put(
                MediaStore.Video.Media.DISPLAY_NAME,
                displayName,
            )

            put(
                MediaStore.Video.Media.MIME_TYPE,
                "video/mp4",
            )

            put(
                MediaStore.Video.Media.RELATIVE_PATH,
                "${Environment.DIRECTORY_MOVIES}/Video Compressor",
            )

            put(
                MediaStore.Video.Media.IS_PENDING,
                1,
            )
        }

        val collection =
            MediaStore.Video.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY,
            )

        val uri = resolver.insert(
            collection,
            values,
        ) ?: throw IOException(
            "Unable to create MediaStore item"
        )

        try {
            resolver.openOutputStream(uri)?.use { output ->
                sourceFile.inputStream().use { input ->
                    input.copyTo(output)
                }
            } ?: throw IOException(
                "Unable to open MediaStore output stream"
            )

            val completedValues =
                ContentValues().apply {
                    put(
                        MediaStore.Video.Media.IS_PENDING,
                        0,
                    )
                }

            resolver.update(
                uri,
                completedValues,
                null,
                null,
            )

            return uri

        } catch (throwable: Throwable) {
            resolver.delete(
                uri,
                null,
                null,
            )

            throw throwable
        }
    }

    fun create(
        originalName: String?,
    ): String {
        val baseName = originalName
            ?.substringBeforeLast('.')
            ?.takeIf { it.isNotBlank() }
            ?: "Video"

        return "${baseName}_compressed.mp4"
    }
}

