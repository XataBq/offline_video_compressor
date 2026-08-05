package com.example.offlinevideocompressor

import android.os.Bundle
import androidx.activity.ComponentActivity
import timber.log.Timber

open class BaseActivity : ComponentActivity() {

    private val activityName: String
        get() = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(LIFECYCLE_TAG).d("$activityName: onCreate")
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(LIFECYCLE_TAG).d("$activityName: onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(LIFECYCLE_TAG).d("$activityName: onResume")
    }

    override fun onPause() {
        Timber.tag(LIFECYCLE_TAG).d("$activityName: onPause")
        super.onPause()
    }

    override fun onStop() {
        Timber.tag(LIFECYCLE_TAG).d("$activityName: onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Timber.tag(LIFECYCLE_TAG).d("$activityName: onDestroy")
        super.onDestroy()
    }

    override fun onRestart() {
        super.onRestart()
        Timber.tag(LIFECYCLE_TAG).d("$activityName: onRestart")
    }

    private companion object {
        const val LIFECYCLE_TAG = "ActivityLifecycle"
    }
}
