package com.example.feature.video.compressor.ui.presentation

import androidx.lifecycle.ViewModel
import com.example.feature_video_compressor_api.usecase.CompressVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class VideoCompressorViewModel @Inject constructor(
    private val compressVideoUseCase: CompressVideoUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(VideoCompressorState.OnboardingScreen)
    val state = _state.asStateFlow()

    fun onSettingsClick() {}
    fun onChooseVideoClick() {}
}