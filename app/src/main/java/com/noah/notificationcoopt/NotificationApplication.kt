package com.noah.notificationcoopt

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi

class NotificationApplication : Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        val notifId = "Notification example"
        val notifName = "Notif example"
        // Creates a new channel for the notification to go through. Required on Android 8.0 or later.
        val channel = NotificationChannel(
            notifId,
            notifName,
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = "An example of a basic notification"

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}