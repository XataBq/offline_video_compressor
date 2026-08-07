package com.example.feature_video_compressor_impl.di

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import com.example.feature_video_compressor_api.usecase.CompressVideoUseCase
import com.example.feature_video_compressor_impl.compressor.Media3VideoCompressor
import com.example.feature_video_compressor_impl.compressor.VideoCompressor
import com.example.feature_video_compressor_impl.domain.DefaultCompressVideoUseCase
import com.example.feature_video_compressor_impl.metadata.AndroidVideoMetadataReader
import com.example.feature_video_compressor_api.usecase.VideoMetadataReader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CompressionModule {

    @Binds
    abstract fun bindCompressVideoUseCase(
        implementation: DefaultCompressVideoUseCase,
    ): CompressVideoUseCase

    @OptIn(UnstableApi::class)
    @Binds
    abstract fun bindVideoCompressor(
        implementation: Media3VideoCompressor,
    ): VideoCompressor

    @Binds
    abstract fun bindVideoMetadataReader(
        implementation: AndroidVideoMetadataReader,
    ): VideoMetadataReader
}