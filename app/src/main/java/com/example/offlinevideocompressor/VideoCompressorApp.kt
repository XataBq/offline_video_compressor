package com.example.offlinevideocompressor

import android.app.Application
import timber.log.Timber

class VideoCompressorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}