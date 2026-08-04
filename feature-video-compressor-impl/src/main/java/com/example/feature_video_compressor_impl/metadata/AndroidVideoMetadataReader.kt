package com.example.feature_video_compressor_impl.metadata

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
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

            val frameRate = retriever.extractMetadata(
                MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE,
            )?.toFloatOrNull()
                ?.roundToInt()
                ?.takeIf { it > 0 }
                ?: DEFAULT_FRAME_RATE

            val isRotated = rotation == 90 || rotation == 270

            return VideoMetadata(
                width = if (isRotated) rawHeight else rawWidth,
                height = if (isRotated) rawWidth else rawHeight,
                frameRate = frameRate,
                bitrate = bitrate,
                durationMs = durationMs,
            )
        } finally {
            retriever.release()
        }
    }

    private fun MediaMetadataRetriever.extractInt(key: Int): Int {
        return extractMetadata(key)?.toIntOrNull() ?: 0
    }

    private fun MediaMetadataRetriever.extractLong(key: Int): Long {
        return extractMetadata(key)?.toLongOrNull() ?: 0L
    }

    private companion object {
        const val DEFAULT_FRAME_RATE = 30
    }
}