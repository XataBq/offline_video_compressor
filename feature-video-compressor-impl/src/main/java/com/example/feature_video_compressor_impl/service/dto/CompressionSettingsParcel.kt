package com.example.feature_video_compressor_impl.service.dto

import android.os.Parcelable
import com.example.feature_video_compressor_api.model.CompressionQuality
import com.example.feature_video_compressor_api.model.VideoCodec
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class CompressionSettingsParcel(
    val resolution: VideoResolutionParcel,
    val frameRate: VideoFrameRateParcel,
    val quality: CompressionQuality,
    val codec: VideoCodec,
    val keepAudio: Boolean,
) : Parcelable