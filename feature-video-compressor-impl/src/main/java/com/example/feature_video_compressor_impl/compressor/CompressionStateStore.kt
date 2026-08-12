package com.example.feature_video_compressor_impl.compressor

import com.example.feature_video_compressor_api.model.CompressionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompressionStateStore @Inject constructor() {

    private val _state = MutableStateFlow<CompressionState>(CompressionState.Idle)

    val state: StateFlow<CompressionState> = _state.asStateFlow()

    fun update(
        state: CompressionState,
    ) {
        _state.value = state
    }
}