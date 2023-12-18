package com.example.bringtodo.backend

import android.app.NotificationManager
import android.content.Context
import androidx.compose.foundation.layout.ColumnScope
import androidx.core.app.NotificationCompat
import com.example.bringtodo.R
import kotlin.random.Random

class NotificationService(private val context: Context) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    fun showBasicNotification() {
        val notification=NotificationCompat.Builder(context,"Test_notif")
            .setContentTitle("Test Notif")
            .setContentText("Test Notif")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }
}