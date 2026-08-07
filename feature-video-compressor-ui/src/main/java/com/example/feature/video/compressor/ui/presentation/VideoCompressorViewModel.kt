package com.example.feature.video.compressor.ui.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.video.compressor.ui.presentation.state.VideoCompressorState
import com.example.feature.video.compressor.ui.presentation.state.VideoCompressorUiState
import com.example.feature_video_compressor_api.model.CompressionPreset
import com.example.feature_video_compressor_api.model.CompressionQuality
import com.example.feature_video_compressor_api.model.CompressionSettings
import com.example.feature_video_compressor_api.model.VideoCodec
import com.example.feature_video_compressor_api.model.VideoFrameRate
import com.example.feature_video_compressor_api.model.VideoResolution
import com.example.feature_video_compressor_api.usecase.CompressVideoUseCase
import com.example.feature_video_compressor_api.usecase.VideoMetadataReader
import com.example.feature_video_compressor_api.usecase.VideoThumbnailProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.roundToLong

@HiltViewModel
internal class VideoCompressorViewModel @Inject constructor(
    private val compressVideoUseCase: CompressVideoUseCase,
    private val videoMetadataReader: VideoMetadataReader,
    private val thumbnailProvider: VideoThumbnailProvider,
) : ViewModel() {
    private val _state = MutableStateFlow(VideoCompressorState.OnboardingScreen)
    val state = _state.asStateFlow()

    private val _uiState = MutableStateFlow(VideoCompressorUiState())
    val uiState = _uiState.asStateFlow()

    private val _news = Channel<VideoCompressorNews>(
        capacity = Channel.BUFFERED
    )
    val news = _news.receiveAsFlow()

    fun onSettingsClick() {
        viewModelScope.launch {
            _news.send(VideoCompressorNews.NavigateSettings)
        }
    }

    fun onChooseVideoClick() {
        viewModelScope.launch {
            _news.send(VideoCompressorNews.OpenVideoPicker)
        }
    }

    fun onVideoChosen(uri: Uri) {
        _uiState.update {
            it.copy(uri = uri)
        }
        Timber.d("Video was chosen")
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    videoMetadata = videoMetadataReader.read(uri)
                )
            }
            Timber.d("videoMetadata updated")

            _news.send(VideoCompressorNews.NavigateCompressionSettings)

            val videoPreview = thumbnailProvider.getThumbnail(
                uri = uri,
                timeMs = 3000L.let {
                    val duration = uiState.value.videoMetadata?.durationMs
                    if ( duration == null) it
                    else minOf(duration, it)
                }
            )
            _uiState.update { it.copy(videoPreview = videoPreview) }

            recalculateEstimatedSize()
        }


    }

    fun onPresetSelected(
        preset: CompressionPreset,
    ) {
        _uiState.update { state ->
            state.copy(
                compressionPreset = preset,
            )
        }

        recalculateEstimatedSize()
    }

    fun onCompressClick() {
        val state = _uiState.value
        val uri = state.uri ?: return
        val settings = state.compressionPreset.toSettings()

        // запуск compressVideoUseCase
    }

    private fun recalculateEstimatedSize() {
        val state = _uiState.value
        val originalSizeMb = state.videoMetadata?.sizeBytes?.let { it / (1024.0 * 1024.0) }

        val savingsPercent = when (state.compressionPreset) {
            CompressionPreset.StrongCompression -> 80
            CompressionPreset.Balanced -> 60
            CompressionPreset.MaxQuality -> 30
            is CompressionPreset.Custom -> 45
        }

        val estimatedSizeMb = originalSizeMb?.let {
            (it * (100 - savingsPercent) / 100f).roundToLong()
        }

        _uiState.update {
            it.copy(
                estimatedSizeMb = estimatedSizeMb,
                savingsPercent = savingsPercent,
            )
        }
    }

    fun onCustomSettingsReset() {
        _uiState.update {
            it.copy(
                compressionPreset = CompressionPreset.Balanced
            )
        }
    }

    fun onResolutionSelected(resolution: VideoResolution) {
        updateCustomSettings { settings ->
            settings.copy(
                resolution = resolution,
            )
        }
    }

    fun onFrameRateSelected(frameRate: VideoFrameRate) {
        updateCustomSettings { settings ->
            settings.copy(
                frameRate = frameRate,
            )
        }
    }

    fun onQualitySelected(quality: CompressionQuality) {
        updateCustomSettings { settings ->
            settings.copy(
                quality = quality,
            )
        }
    }

    fun onCodecSelected(codec: VideoCodec) {
        updateCustomSettings { settings ->
            settings.copy(
                codec = codec,
            )
        }
    }

    private fun updateCustomSettings(
        transform: (CompressionSettings) -> CompressionSettings,
    ) {
        _uiState.update { state ->
            val currentSettings = state.compressionSettings
            val newSettings = transform(currentSettings)

            state.copy(
                compressionPreset = CompressionPreset.Custom(
                    settings = newSettings,
                ),
            )
        }

        recalculateEstimatedSize()
    }
}