package com.example.feature_video_compressor_impl.di

import com.example.feature_video_compressor_api.repository.VideoCompressorRepository
import com.example.feature_video_compressor_api.usecase.SimpleCompressionUseCase
import com.example.feature_video_compressor_impl.repository.VideoCompressorRepositoryImpl
import com.example.feature_video_compressor_impl.usecase.SimpleCompressionUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CompressorModule {

    @Binds
    @Singleton
    abstract fun bindCompressorRepository(impl: VideoCompressorRepositoryImpl): VideoCompressorRepository

    @Binds
    @Singleton
    abstract fun bindSimpleCompressionUseCase(impl: SimpleCompressionUseCaseImpl): SimpleCompressionUseCase
}