package com.example.feature_video_compressor_impl.service

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CompressionTempFileFactory @Inject constructor(
    @ApplicationContext
    private val context: Context,
) {

    fun create(): File {
        val directory = File(
            context.cacheDir,
            TEMP_DIRECTORY,
        ).apply {
            mkdirs()
        }

        return File(
            directory,
            "compressed_${System.currentTimeMillis()}.mp4",
        )
    }

    private companion object {
        const val TEMP_DIRECTORY = "video_compression"
    }
}