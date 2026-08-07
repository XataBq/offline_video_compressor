package com.example.feature_video_compressor_api.usecase

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri

interface VideoThumbnailProvider {
    suspend fun getThumbnail(
        uri: Uri,
        timeMs: Long,
    ): Bitmap?
}