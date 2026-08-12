package com.example.feature_video_compressor_impl.di

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import com.example.feature_video_compressor_api.usecase.CancelCompressionUseCase
import com.example.feature_video_compressor_api.usecase.ObserveCompressionUseCase
import com.example.feature_video_compressor_api.usecase.StartCompressionUseCase
import com.example.feature_video_compressor_impl.compressor.Media3VideoCompressor
import com.example.feature_video_compressor_impl.compressor.VideoCompressor
import com.example.feature_video_compressor_impl.domain.DefaultStartCompressionUseCase
import com.example.feature_video_compressor_impl.metadata.AndroidVideoMetadataReader
import com.example.feature_video_compressor_api.usecase.VideoMetadataReader
import com.example.feature_video_compressor_api.usecase.VideoThumbnailProvider
import com.example.feature_video_compressor_impl.domain.CompressionOutputStorage
import com.example.feature_video_compressor_impl.domain.DefaultCancelCompressionUseCase
import com.example.feature_video_compressor_impl.domain.DefaultObserveCompressionUseCase
import com.example.feature_video_compressor_impl.domain.MediaStoreCompressionOutputStorage
import com.example.feature_video_compressor_impl.metadata.AndroidVideoThumbnailProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CompressionModule {

    @OptIn(UnstableApi::class)
    @Binds
    abstract fun bindVideoCompressor(
        implementation: Media3VideoCompressor,
    ): VideoCompressor

    @Binds
    abstract fun bindVideoMetadataReader(
        implementation: AndroidVideoMetadataReader,
    ): VideoMetadataReader

    @Binds
    abstract fun bindVideoThumbnailProvider(
        implementation: AndroidVideoThumbnailProvider,
    ): VideoThumbnailProvider
}

@Module
@InstallIn(SingletonComponent::class)
internal interface CompressionUseCaseModule {

    @Binds
    fun bindStartCompressionUseCase(
        implementation: DefaultStartCompressionUseCase,
    ): StartCompressionUseCase

    @Binds
    fun bindObserveCompressionUseCase(
        implementation: DefaultObserveCompressionUseCase,
    ): ObserveCompressionUseCase

    @Binds
    fun bindCancelCompressionUseCase(
        implementation: DefaultCancelCompressionUseCase,
    ): CancelCompressionUseCase
}

@Module
@InstallIn(SingletonComponent::class)
internal interface CompressionStorageModule {

    @Binds
    fun bindCompressionOutputStorage(
        implementation: MediaStoreCompressionOutputStorage,
    ): CompressionOutputStorage
}