package com.example.feature.video.compressor.ui.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_video_compressor_api.usecase.CompressVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class VideoCompressorViewModel @Inject constructor(
    private val compressVideoUseCase: CompressVideoUseCase
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
            _news.send(VideoCompressorNews.NavigateCompressionSettings)
        }
    }
}