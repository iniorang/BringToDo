package com.example.bringtodo.backend

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.bringtodo.R
import kotlin.random.Random

class NotifWorker(context : Context, params: WorkerParameters) : Worker(context,params) {
    override fun doWork(): Result {
        Log.d("Work Success","Do work: Success")
        val name = inputData.getString("name")
        if(name !=null){
            showNotif(name)
        }
        return Result.success()
    }

    private fun showNotif(name : String?){
        val notifMan = applicationContext.getSystemService(NotificationManager::class.java)
        val notif = NotificationCompat.Builder(applicationContext,"notif_id")
            .setContentTitle("$name Akan segera dimulai")
            .setContentTitle("Siapkan barang anda")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()
        notifMan.notify(Random.nextInt(),notif)
    }
}