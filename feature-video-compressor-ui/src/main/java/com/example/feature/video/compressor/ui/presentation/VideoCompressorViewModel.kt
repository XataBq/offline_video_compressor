package com.example.feature.video.compressor.ui.presentation

import android.annotation.SuppressLint
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.video.compressor.ui.presentation.state.VideoCompressorUiState
import com.example.feature.video.compressor.ui.utils.CompressionEstimateCalculator
import com.example.feature_video_compressor_api.model.CompressionPreset
import com.example.feature_video_compressor_api.model.CompressionQuality
import com.example.feature_video_compressor_api.model.CompressionSettings
import com.example.feature_video_compressor_api.model.CompressionState
import com.example.feature_video_compressor_api.model.VideoCodec
import com.example.feature_video_compressor_api.model.VideoFrameRate
import com.example.feature_video_compressor_api.model.VideoLocation
import com.example.feature_video_compressor_api.model.VideoResolution
import com.example.feature_video_compressor_api.usecase.CancelCompressionUseCase
import com.example.feature_video_compressor_api.usecase.ObserveCompressionUseCase
import com.example.feature_video_compressor_api.usecase.StartCompressionUseCase
import com.example.feature_video_compressor_api.usecase.VideoMetadataReader
import com.example.feature_video_compressor_api.usecase.VideoThumbnailProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class VideoCompressorViewModel @Inject constructor(
    private val startCompressionUseCase: StartCompressionUseCase,
    private val observeCompressionUseCase: ObserveCompressionUseCase,
    private val cancelCompressionUseCase: CancelCompressionUseCase,
    private val videoMetadataReader: VideoMetadataReader,
    private val thumbnailProvider: VideoThumbnailProvider,
    private val compressionEstimateCalculator: CompressionEstimateCalculator,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            VideoCompressorUiState(),
        )

    val uiState =
        _uiState.asStateFlow()

    private val _news =
        Channel<VideoCompressorNews>(
            capacity = Channel.BUFFERED,
        )

    val news =
        _news.receiveAsFlow()

    init {
        observeCompression()
    }

    private fun observeCompression() {
        viewModelScope.launch {
            observeCompressionUseCase()
                .collect { compressionState ->
                    onCompressionStateChanged(
                        compressionState,
                    )
                }
        }
    }

    private fun onCompressionStateChanged(
        state: CompressionState,
    ) {
        when (state) {
            CompressionState.Idle -> {
                Unit
            }

            CompressionState.Preparing -> {
                _uiState.update {
                    it.copy(
                        compressionProgress = 0,
                        compressionTimeLeftMs = null,
                        isCompressing = true,
                        isCompressButtonEnabled = false,
                    )
                }
            }

            is CompressionState.Compressing -> {
                _uiState.update {
                    it.copy(
                        compressionProgress =
                            state.progress,
                        compressionTimeLeftMs =
                            state.remainingTimeMs,
                        isCompressing = true,
                        isCompressButtonEnabled = false,
                    )
                }
            }

            is CompressionState.Completed -> {
                _uiState.update {
                    it.copy(
                        compressionProgress = 100,
                        compressionTimeLeftMs = 0L,
                        isCompressing = false,
                        isCompressButtonEnabled = true,

                        compressedVideoLocation =
                            state.outputLocation,

                        completedOriginalSizeBytes =
                            state.originalSizeBytes,

                        completedCompressedSizeBytes =
                            state.compressedSizeBytes,
                    )
                }

                loadCompressedVideoResult(
                    location = state.outputLocation,
                )
            }

            is CompressionState.Failed -> {
                _uiState.update {
                    it.copy(
                        compressionProgress = null,
                        compressionTimeLeftMs = null,
                        isCompressing = false,
                        isCompressButtonEnabled = true,
                    )
                }

                Timber.e(
                    state.throwable,
                    "Video compression failed",
                )
            }

            CompressionState.Cancelled -> {
                _uiState.update {
                    it.copy(
                        compressionProgress = null,
                        compressionTimeLeftMs = null,
                        isCompressing = false,
                        isCompressButtonEnabled = true,
                    )
                }
            }
        }
    }

    @SuppressLint("UseKtx")
    private fun loadCompressedVideoResult(
        location: VideoLocation,
    ) {
        viewModelScope.launch {
            val uri = Uri.parse(location.value,)

            try {
                val metadata =
                    withContext(Dispatchers.IO) {
                        videoMetadataReader.read(
                            uri,
                        )
                    }

                val previewTimeMs =
                    minOf(
                        metadata.durationMs,
                        PREVIEW_TIME_MS,
                    )

                val preview =
                    withContext(Dispatchers.IO) {
                        thumbnailProvider.getThumbnail(
                            uri = uri,
                            timeMs = previewTimeMs,
                        )
                    }

                _uiState.update {
                    it.copy(
                        compressedVideoMetadata =
                            metadata,
                        compressedVideoPreview =
                            preview,
                    )
                }
            } catch (throwable: Throwable) {
                Timber.e(
                    throwable,
                    "Failed to read compressed video",
                )
            } finally {
                _news.send(
                    VideoCompressorNews
                        .NavigateCompressionResult,
                )
            }
        }
    }

    @SuppressLint("UseKtx")
    fun onEditCompressedVideoClick() {
        val outputLocation =
            _uiState.value
                .compressedVideoLocation
                ?: return

        val uri = Uri.parse(outputLocation.value)

        onVideoChosen(
            uri = uri,
        )
    }

    fun onChooseVideoClick() {
        viewModelScope.launch {
            _news.send(
                VideoCompressorNews.OpenVideoPicker,
            )
        }
    }

    fun onVideoChosen(
        uri: Uri,
    ) {
        _uiState.update {
            it.copy(
                uri = uri,

                videoMetadata = null,
                videoPreview = null,

                estimatedSizeMb = null,
                savingsPercent = null,

                compressionProgress = null,
                compressionTimeLeftMs = null,

                compressedVideoLocation = null,
                compressedVideoMetadata = null,
                compressedVideoPreview = null,
                completedOriginalSizeBytes = null,
                completedCompressedSizeBytes = null,

                isCompressing = false,
                isCompressButtonEnabled = true,
            )
        }

        viewModelScope.launch {
            _news.send(
                VideoCompressorNews
                    .NavigateCompressionSettings,
            )

            try {
                val metadata =
                    withContext(Dispatchers.IO) {
                        videoMetadataReader.read(uri)
                    }

                _uiState.update {
                    it.copy(
                        videoMetadata = metadata,
                    )
                }

                val previewTimeMs =
                    minOf(
                        metadata.durationMs,
                        PREVIEW_TIME_MS,
                    )

                val videoPreview =
                    withContext(Dispatchers.IO) {
                        thumbnailProvider.getThumbnail(
                            uri = uri,
                            timeMs = previewTimeMs,
                        )
                    }

                _uiState.update {
                    it.copy(
                        videoPreview = videoPreview,
                    )
                }

                recalculateEstimatedSize()
            } catch (throwable: Throwable) {
                Timber.e(
                    throwable,
                    "Failed to load selected video",
                )
            }
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
        val state =
            _uiState.value

        val uri =
            state.uri
                ?: return

        if (
            state.isCompressing ||
            !state.isCompressButtonEnabled
        ) {
            return
        }

        _uiState.update {
            it.copy(
                compressionProgress = 0,
                compressionTimeLeftMs = null,
                isCompressing = true,
                isCompressButtonEnabled = false,
                compressedVideoLocation = null,
            )
        }

        try {
            startCompressionUseCase(
                inputLocation = VideoLocation(
                    value = uri.toString(),
                ),
                preset = state.compressionPreset,
            )

            viewModelScope.launch {
                _news.send(
                    VideoCompressorNews
                        .NavigateCompressionProgress,
                )
            }
        } catch (throwable: Throwable) {
            _uiState.update {
                it.copy(
                    compressionProgress = null,
                    compressionTimeLeftMs = null,
                    isCompressing = false,
                    isCompressButtonEnabled = true,
                )
            }

            Timber.e(
                throwable,
                "Failed to start video compression",
            )
        }
    }

    fun onCustomSettingsReset() {
        _uiState.update {
            it.copy(
                compressionPreset =
                    CompressionPreset.Balanced,
            )
        }

        recalculateEstimatedSize()
    }

    fun onResolutionSelected(
        resolution: VideoResolution,
    ) {
        updateCustomSettings { settings ->
            settings.copy(
                resolution = resolution,
            )
        }
    }

    fun onFrameRateSelected(
        frameRate: VideoFrameRate,
    ) {
        updateCustomSettings { settings ->
            settings.copy(
                frameRate = frameRate,
            )
        }
    }

    fun onQualitySelected(
        quality: CompressionQuality,
    ) {
        updateCustomSettings { settings ->
            settings.copy(
                quality = quality,
            )
        }
    }

    fun onCodecSelected(
        codec: VideoCodec,
    ) {
        updateCustomSettings { settings ->
            settings.copy(
                codec = codec,
            )
        }
    }

    private fun updateCustomSettings(
        transform:
            (CompressionSettings) ->
        CompressionSettings,
    ) {
        _uiState.update { state ->
            val currentSettings =
                state.compressionSettings

            val newSettings =
                transform(
                    currentSettings,
                )

            state.copy(
                compressionPreset =
                    CompressionPreset.Custom(
                        settings = newSettings,
                    ),
            )
        }

        recalculateEstimatedSize()
    }

    private fun recalculateEstimatedSize() {
        val state =
            _uiState.value

        val estimate =
            compressionEstimateCalculator.calculate(
                metadata = state.videoMetadata,
                settings = state.compressionSettings,
            )

        _uiState.update {
            it.copy(
                estimatedSizeMb =
                    estimate.estimatedSizeMb,
                savingsPercent =
                    estimate.savingsPercent,
            )
        }
    }

    fun onCancelClick() {
        if (!_uiState.value.isCompressing) {
            return
        }

        cancelCompressionUseCase()
    }

    private companion object {

        const val PREVIEW_TIME_MS =
            3_000L
    }
}