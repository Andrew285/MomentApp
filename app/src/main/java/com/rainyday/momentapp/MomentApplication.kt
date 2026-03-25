package com.rainyday.momentapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

const val NOTIFICATION_CHANNEL_DEFAULT = "default_channel"

@HiltAndroidApp
class MomentApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
    }

    private fun initTimber() {
        if (BuildConfig.ENABLE_LOGS) {
            Timber.plant()
        } else {

        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_DEFAULT,
                "General Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "App general notifications"
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}