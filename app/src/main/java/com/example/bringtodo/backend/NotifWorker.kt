package com.example.bringtodo.backend

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.bringtodo.R
import kotlin.random.Random

class NotifWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        Log.d("Work Success", "Do work: Success")
        val name = inputData.getString("acaraName")
        val bawaan = inputData.getString("bawaan")?.split(", ")?.joinToString(separator = "\n") { it.trim() }
        if (name != null) {
            showNotifAcara(name,bawaan)
        }
        if (!bawaan.isNullOrEmpty()) {
//            showNotifBarang(name, bawaan)
        }
        return Result.success()
    }

    private fun showNotifAcara(nama :String,bawaan: String?) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "Acara_Channel"
        val notificationId = Random.nextInt()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Acara Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("$nama akan dimulai")
            .setContentText("Jangan Lupa siapkan dan bawa : \n$bawaan")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle())
            .build()

        notificationManager.notify(notificationId, notification)
    }
}