package com.example.feature.video.compressor.ui.presentation.screens

internal sealed interface VideoCompressorNews {
    object NavigateSettings : VideoCompressorNews
    object NavigateCompressionSettings : VideoCompressorNews
}