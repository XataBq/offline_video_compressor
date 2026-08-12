package com.example.feature_video_compressor_impl.service

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import com.example.feature_video_compressor_api.model.CompressionSettings
import com.example.feature_video_compressor_api.model.VideoLocation
import com.example.feature_video_compressor_impl.service.mappers.toParcel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CompressionServiceController @Inject constructor(
    @ApplicationContext
    private val context: Context,
) {

    fun start(
        inputLocation: VideoLocation,
        settings: CompressionSettings,
    ) {
        val intent = Intent(
            context,
            VideoCompressionService::class.java,
        ).apply {
            action =
                VideoCompressionService.ACTION_START

            putExtra(
                VideoCompressionService.EXTRA_INPUT_LOCATION,
                inputLocation.value,
            )

            putExtra(
                VideoCompressionService.EXTRA_COMPRESSION_SETTINGS,
                settings.toParcel(),
            )
        }

        ContextCompat.startForegroundService(
            context,
            intent,
        )
    }

    fun cancel() {
        val intent = Intent(
            context,
            VideoCompressionService::class.java,
        ).apply {
            action =
                VideoCompressionService.ACTION_CANCEL
        }

        context.startService(intent)
    }
}