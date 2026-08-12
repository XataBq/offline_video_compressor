package com.example.feature.video.compressor.ui.presentation

internal sealed interface VideoCompressorNews {

    data object NavigateCompressionSettings :
        VideoCompressorNews

    data object NavigateCompressionProgress :
        VideoCompressorNews

    data object NavigateCompressionResult :
        VideoCompressorNews

    data object OpenVideoPicker :
        VideoCompressorNews
}