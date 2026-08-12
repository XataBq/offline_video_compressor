package com.example.feature_video_compressor_impl.service.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal sealed interface VideoResolutionParcel : Parcelable {

    @Parcelize
    data object Original : VideoResolutionParcel

    @Parcelize
    data class TargetShortSide(
        val pixels: Int,
    ) : VideoResolutionParcel
}