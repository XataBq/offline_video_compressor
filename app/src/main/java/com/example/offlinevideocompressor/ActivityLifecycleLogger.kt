package com.example.offlinevideocompressor

import android.app.Activity
import android.app.Application
import android.os.Bundle
import timber.log.Timber

class ActivityLifecycleLogger :
    Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(
        activity: Activity,
        savedInstanceState: Bundle?,
    ) {
        activity.logLifecycle("onCreate")
    }

    override fun onActivityStarted(activity: Activity) {
        activity.logLifecycle("onStart")
    }

    override fun onActivityResumed(activity: Activity) {
        activity.logLifecycle("onResume")
    }

    override fun onActivityPaused(activity: Activity) {
        activity.logLifecycle("onPause")
    }

    override fun onActivityStopped(activity: Activity) {
        activity.logLifecycle("onStop")
    }

    override fun onActivitySaveInstanceState(
        activity: Activity,
        outState: Bundle,
    ) {
        activity.logLifecycle("onSaveInstanceState")
    }

    override fun onActivityDestroyed(activity: Activity) {
        activity.logLifecycle("onDestroy")
    }

    private fun Activity.logLifecycle(event: String) {
        Timber
            .tag("ActivityLifecycle")
            .d("${javaClass.simpleName}: $event")
    }
}