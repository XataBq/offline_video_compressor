package com.example.feature.video.compressor.ui.presentation

internal sealed interface VideoCompressorNews {
    object NavigateSettings : VideoCompressorNews
    object NavigateCompressionSettings : VideoCompressorNews
    object OpenVideoPicker : VideoCompressorNews
}