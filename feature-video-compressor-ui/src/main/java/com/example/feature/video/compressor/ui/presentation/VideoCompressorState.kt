package com.example.feature.video.compressor.ui.presentation

internal sealed interface VideoCompressorState {
    object Compression: VideoCompressorState
    object OnboardingScreen: VideoCompressorState
}