package com.example.feature_video_compressor_impl.service.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal sealed interface VideoFrameRateParcel : Parcelable {

    @Parcelize
    data object Original : VideoFrameRateParcel

    @Parcelize
    data class Limited(
        val fps: Int,
    ) : VideoFrameRateParcel
}