package com.example.feature_video_compressor_impl.metadata

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import com.example.feature_video_compressor_api.usecase.VideoThumbnailProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AndroidVideoThumbnailProvider @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : VideoThumbnailProvider {

    override suspend fun getThumbnail(
        uri: Uri,
        timeMs: Long
    ): Bitmap? = withContext(Dispatchers.IO) {
        val retrieverInternal = MediaMetadataRetriever()

        try {
            retrieverInternal.setDataSource(context, uri)

            retrieverInternal.getFrameAtTime(
                timeMs,
                MediaMetadataRetriever.OPTION_CLOSEST_SYNC
                )
        } finally {
            retrieverInternal.release()
        }
    }
}