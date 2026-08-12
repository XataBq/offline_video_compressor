package com.example.feature_video_compressor_impl.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.feature_video_compressor_impl.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CompressionNotificationFactory @Inject constructor(
    @ApplicationContext
    private val context: Context,
) {

    fun createChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(
                R.string.compression_notification_channel_name,
            ),
            NotificationManager.IMPORTANCE_LOW,
        ).apply {
            description = context.getString(
                R.string.compression_notification_channel_description,
            )
        }

        context
            .getSystemService(NotificationManager::class.java)
            .createNotificationChannel(channel)
    }

    fun progress(
        progress: Int,
        indeterminate: Boolean,
    ): Notification {
        val contentText =
            if (indeterminate) {
                context.getString(
                    R.string.compression_notification_preparing,
                )
            } else {
                context.getString(
                    R.string.compression_notification_progress,
                    progress.coerceIn(0, 100),
                )
            }

        return baseBuilder()
            .setCategory(
                NotificationCompat.CATEGORY_PROGRESS,
            )
            .setContentTitle(
                context.getString(
                    R.string.compression_notification_title,
                )
            )
            .setContentText(contentText)
            .setProgress(
                100,
                progress.coerceIn(0, 100),
                indeterminate,
            )
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .addAction(
                R.drawable.ic_cancel,
                context.getString(
                    R.string.compression_notification_cancel,
                ),
                cancelPendingIntent(),
            )
            .build()
    }

    fun updateProgress(
        notificationId: Int,
        progress: Int,
        indeterminate: Boolean,
    ) {
        postIfAllowed(
            notificationId = notificationId,
            notification = progress(
                progress = progress,
                indeterminate = indeterminate,
            ),
        )
    }

    fun showCompleted() {
        val notification = baseBuilder()
            .setContentTitle(
                context.getString(
                    R.string.compression_notification_completed_title,
                )
            )
            .setContentText(
                context.getString(
                    R.string.compression_notification_completed_description,
                )
            )
            .setAutoCancel(true)
            .build()

        postIfAllowed(
            notificationId =
                COMPLETED_NOTIFICATION_ID,
            notification = notification,
        )
    }

    fun showFailed() {
        val notification = baseBuilder()
            .setContentTitle(
                context.getString(
                    R.string.compression_notification_failed_title,
                )
            )
            .setContentText(
                context.getString(
                    R.string.compression_notification_failed_description,
                )
            )
            .setAutoCancel(true)
            .build()

        postIfAllowed(
            notificationId =
                FAILED_NOTIFICATION_ID,
            notification = notification,
        )
    }

    private fun baseBuilder(): NotificationCompat.Builder {
        return NotificationCompat.Builder(
            context,
            CHANNEL_ID,
        )
            .setSmallIcon(
                R.drawable.ic_compression_notification,
            )
            .setPriority(
                NotificationCompat.PRIORITY_LOW,
            )
    }

    private fun cancelPendingIntent(): PendingIntent {
        val intent = Intent(
            context,
            VideoCompressionService::class.java,
        ).apply {
            action =
                VideoCompressionService.ACTION_CANCEL
        }

        return PendingIntent.getService(
            context,
            CANCEL_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                PendingIntent.FLAG_IMMUTABLE,
        )
    }

    @SuppressLint("MissingPermission")
    private fun postIfAllowed(
        notificationId: Int,
        notification: Notification,
    ) {
        if (!canPostNotifications()) {
            return
        }

        NotificationManagerCompat
            .from(context)
            .notify(
                notificationId,
                notification,
            )
    }

    private fun canPostNotifications(): Boolean {
        return Build.VERSION.SDK_INT <
            Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
    }

    private companion object {

        const val CHANNEL_ID =
            "video_compression"

        const val COMPLETED_NOTIFICATION_ID =
            1002

        const val FAILED_NOTIFICATION_ID =
            1003

        const val CANCEL_REQUEST_CODE =
            2001
    }
}