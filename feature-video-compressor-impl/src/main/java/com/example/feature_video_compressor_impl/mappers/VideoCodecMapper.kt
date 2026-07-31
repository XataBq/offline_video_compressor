package com.example.feature_video_compressor_impl.mappers

import androidx.media3.common.MimeTypes
import com.example.feature_video_compressor_api.model.VideoCodec

internal fun VideoCodec.toMimeType(): String {
    return when (this) {
        VideoCodec.H264 -> MimeTypes.VIDEO_H264
        VideoCodec.H265 -> MimeTypes.VIDEO_H265
    }
}