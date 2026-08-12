package com.example.feature.video.compressor.ui.presentation.state

import android.graphics.Bitmap
import android.net.Uri
import com.example.feature_video_compressor_api.model.CompressionPreset
import com.example.feature_video_compressor_api.model.CompressionSettings
import com.example.feature_video_compressor_api.model.VideoLocation
import com.example.feature_video_compressor_api.model.VideoMetadata
import kotlin.math.roundToInt

internal data class VideoCompressorUiState(
    val uri: Uri? = null,

    val videoMetadata: VideoMetadata? = null,

    val compressionTimeLeftMs: Long? = null,
    val compressionProgress: Int? = null,

    val compressionPreset: CompressionPreset =
        CompressionPreset.Balanced,

    val estimatedSizeMb: Long? = null,
    val savingsPercent: Int? = null,

    val isCompressButtonEnabled: Boolean = true,

    val videoPreview: Bitmap? = null,

    val isCompressing: Boolean = false,

    val compressedVideoLocation: VideoLocation? = null,
    val compressedVideoMetadata: VideoMetadata? = null,
    val compressedVideoPreview: Bitmap? = null,

    val completedOriginalSizeBytes: Long? = null,
    val completedCompressedSizeBytes: Long? = null,
) {

    val compressionSettings: CompressionSettings
        get() = compressionPreset.toSettings()

    val originalSizeMb: Double?
        get() = videoMetadata
            ?.sizeBytes
            ?.let { bytes ->
                bytes / BYTES_PER_MEGABYTE
            }

    val compressedSizeMb: Double?
        get() = completedCompressedSizeBytes
            ?.let { bytes ->
                bytes / BYTES_PER_MEGABYTE
            }

    val actualSavingsPercent: Int?
        get() {
            val original =
                completedOriginalSizeBytes
                    ?: return null

            val compressed =
                completedCompressedSizeBytes
                    ?: return null

            if (original <= 0L) {
                return null
            }

            return (
                (1.0 - compressed.toDouble() / original) *
                    100.0
                )
                .roundToInt()
                .coerceIn(
                    0,
                    100,
                )
        }

    private companion object {
        const val BYTES_PER_MEGABYTE =
            1024.0 * 1024.0
    }
}