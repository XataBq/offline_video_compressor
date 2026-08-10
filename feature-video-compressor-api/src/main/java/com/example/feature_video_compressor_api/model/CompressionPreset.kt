package com.example.feature_video_compressor_api.model

sealed interface CompressionPreset {

    fun toSettings(): CompressionSettings

    data object StrongCompression : CompressionPreset {
        override fun toSettings(): CompressionSettings {
            return CompressionSettings(
                resolution = VideoResolution.TargetShortSide(480),
                frameRate = VideoFrameRate.Limited(24),
                quality = CompressionQuality.MAX_COMPRESSION,
                codec = VideoCodec.H264,
            )
        }
    }

    data object Balanced : CompressionPreset {
        override fun toSettings(): CompressionSettings {
            return CompressionSettings(
                resolution = VideoResolution.TargetShortSide(720),
                frameRate = VideoFrameRate.Limited(30),
                quality = CompressionQuality.MEDIUM,
                codec = VideoCodec.H264,
            )
        }
    }

    data object MaxQuality : CompressionPreset {
        override fun toSettings(): CompressionSettings {
            return CompressionSettings(
                resolution = VideoResolution.Original,
                frameRate = VideoFrameRate.Original,
                quality = CompressionQuality.HIGH,
                codec = VideoCodec.H264,
            )
        }
    }

    data class Custom(
        private val settings: CompressionSettings
    ) : CompressionPreset {
        override fun toSettings(): CompressionSettings {
            return settings
        }
    }
}