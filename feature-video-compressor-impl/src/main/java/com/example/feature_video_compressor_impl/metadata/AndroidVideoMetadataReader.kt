package com.example.feature_video_compressor_impl.metadata

import android.content.Context
import android.media.MediaExtractor
import android.media.MediaFormat
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.OpenableColumns
import com.example.feature_video_compressor_api.model.VideoMetadata
import com.example.feature_video_compressor_api.usecase.VideoMetadataReader
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.math.roundToInt

class AndroidVideoMetadataReader @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : VideoMetadataReader {

    override fun read(uri: Uri): VideoMetadata {
        val retriever = MediaMetadataRetriever()

        try {
            retriever.setDataSource(context, uri)

            val rawWidth = retriever.extractInt(
                MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH,
            )

            val rawHeight = retriever.extractInt(
                MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT,
            )

            val rotation = retriever.extractInt(
                MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION,
            )

            val durationMs = retriever.extractLong(
                MediaMetadataRetriever.METADATA_KEY_DURATION,
            )

            val bitrate = retriever.extractMetadata(
                MediaMetadataRetriever.METADATA_KEY_BITRATE,
            )?.toIntOrNull()

            val frameRate = extractFrameRate(uri)
                ?: retriever.extractMetadata(
                    MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE,
                )
                    ?.toFloatOrNull()
                    ?.roundToInt()
                    ?.takeIf { it > 0 }

            val isRotated = rotation == 90 || rotation == 270

            return VideoMetadata(
                width = if (isRotated) rawHeight else rawWidth,
                height = if (isRotated) rawWidth else rawHeight,
                frameRate = frameRate,
                bitrate = bitrate,
                durationMs = durationMs,
                sizeBytes = getFileSize(uri),
                fileName = getFileName(uri),
            )
        } finally {
            retriever.release()
        }
    }

    private fun extractFrameRate(uri: Uri): Int? {
        val extractor = MediaExtractor()

        return try {
            context.contentResolver
                .openFileDescriptor(uri, "r")
                ?.use { descriptor ->
                    extractor.setDataSource(
                        descriptor.fileDescriptor,
                    )

                    repeat(extractor.trackCount) { trackIndex ->
                        val format = extractor.getTrackFormat(trackIndex)

                        val mimeType = format.getString(
                            MediaFormat.KEY_MIME,
                        )

                        if (mimeType?.startsWith(VIDEO_MIME_PREFIX) == true) {
                            return@use format
                                .takeIf {
                                    it.containsKey(
                                        MediaFormat.KEY_FRAME_RATE,
                                    )
                                }
                                ?.getInteger(
                                    MediaFormat.KEY_FRAME_RATE,
                                )
                                ?.takeIf { it > 0 }
                        }
                    }

                    null
                }
        } finally {
            extractor.release()
        }
    }

    private fun getFileSize(uri: Uri): Long? {
        return context.contentResolver
            .openAssetFileDescriptor(
                uri,
                READ_MODE,
            )
            ?.use { descriptor ->
                descriptor.length.takeIf { it >= 0L }
            }
    }

    private fun getFileName(uri: Uri): String? {
        return context.contentResolver
            .query(
                uri,
                arrayOf(OpenableColumns.DISPLAY_NAME),
                null,
                null,
                null,
            )
            ?.use { cursor ->
                if (!cursor.moveToFirst()) {
                    return@use null
                }

                val index = cursor.getColumnIndex(
                    OpenableColumns.DISPLAY_NAME,
                )

                if (index == -1) {
                    return@use null
                }

                cursor.getString(index)
            }
    }

    private fun MediaMetadataRetriever.extractInt(
        key: Int,
    ): Int {
        return extractMetadata(key)
            ?.toIntOrNull()
            ?: 0
    }

    private fun MediaMetadataRetriever.extractLong(
        key: Int,
    ): Long {
        return extractMetadata(key)
            ?.toLongOrNull()
            ?: 0L
    }

    private companion object {
        const val VIDEO_MIME_PREFIX = "video/"
        const val READ_MODE = "r"
    }
}