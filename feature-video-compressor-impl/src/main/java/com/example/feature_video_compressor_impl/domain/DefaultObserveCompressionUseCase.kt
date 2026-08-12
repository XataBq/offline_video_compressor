package com.example.feature_video_compressor_impl.domain

import com.example.feature_video_compressor_api.model.CompressionState
import com.example.feature_video_compressor_api.usecase.ObserveCompressionUseCase
import com.example.feature_video_compressor_impl.compressor.CompressionStateStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class DefaultObserveCompressionUseCase @Inject constructor(
    private val stateStore: CompressionStateStore,
) : ObserveCompressionUseCase {

    override fun invoke(): Flow<CompressionState> {
        return stateStore.state
    }
}