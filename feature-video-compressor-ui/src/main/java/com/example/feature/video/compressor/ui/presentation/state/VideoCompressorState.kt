package com.example.feature.video.compressor.ui.presentation.state

internal sealed interface VideoCompressorState {
    object Compression: VideoCompressorState
    object OnboardingScreen: VideoCompressorState
}