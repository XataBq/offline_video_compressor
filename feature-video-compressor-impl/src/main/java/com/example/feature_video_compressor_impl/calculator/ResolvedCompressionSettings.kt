package com.example.feature_video_compressor_impl.calculator

import com.example.feature_video_compressor_api.model.VideoCodec

data class ResolvedCompressionSettings(
    val width: Int,
    val height: Int,
    val frameRate: Int,
    val bitrate: Int,
    val codec: VideoCodec,
    val keepAudio: Boolean,
    val shouldResize: Boolean,
    val shouldLimitFrameRate: Boolean,
)