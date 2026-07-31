package com.example.feature_video_compressor_impl.mappers

import com.example.feature_video_compressor_api.model.CompressionQuality

internal fun CompressionQuality.toBitsPerPixel(): Double {
    return when (this) {
        CompressionQuality.MAX_COMPRESSION -> 0.040
        CompressionQuality.LOW -> 0.055
        CompressionQuality.MEDIUM -> 0.080
        CompressionQuality.HIGH -> 0.110
        CompressionQuality.MAX_QUALITY -> 0.140
    }
}