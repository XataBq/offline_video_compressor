package com.example.feature_video_compressor_impl.preset

import com.example.feature_video_compressor_api.model.CompressionPreset
import com.example.feature_video_compressor_api.model.CompressionQuality
import com.example.feature_video_compressor_api.model.CompressionSettings
import com.example.feature_video_compressor_api.model.VideoCodec
import com.example.feature_video_compressor_api.model.VideoFrameRate
import com.example.feature_video_compressor_api.model.VideoResolution
import javax.inject.Inject

class CompressionPresetFactory @Inject constructor(){

    fun create(preset: CompressionPreset): CompressionSettings {
        return when (preset) {
            CompressionPreset.STRONG_COMPRESSION -> {
                CompressionSettings(
                    resolution = VideoResolution.TargetHeight(480),
                    frameRate = VideoFrameRate.Limited(24),
                    quality = CompressionQuality.MAX_COMPRESSION,
                    codec = VideoCodec.H264,
                )
            }

            CompressionPreset.BALANCED -> {
                CompressionSettings(
                    resolution = VideoResolution.TargetHeight(720),
                    frameRate = VideoFrameRate.Limited(30),
                    quality = CompressionQuality.MEDIUM,
                    codec = VideoCodec.H264,
                )
            }

            CompressionPreset.MAX_QUALITY -> {
                CompressionSettings(
                    resolution = VideoResolution.Original,
                    frameRate = VideoFrameRate.Original,
                    quality = CompressionQuality.HIGH,
                    codec = VideoCodec.H264,
                )
            }
        }
    }
}