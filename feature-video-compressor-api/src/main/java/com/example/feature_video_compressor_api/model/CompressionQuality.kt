package com.example.feature_video_compressor_api.model

enum class CompressionQuality(
    val bitsPerPixel: Double,
) {
    MAX_COMPRESSION(
        bitsPerPixel = 0.040,
    ),

    LOW(
        bitsPerPixel = 0.055,
    ),

    MEDIUM(
        bitsPerPixel = 0.080,
    ),

    HIGH(
        bitsPerPixel = 0.110,
    ),

    MAX_QUALITY(
        bitsPerPixel = 0.140,
    ),
}