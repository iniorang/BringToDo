package com.example.bringtodo.backend

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

object NotifHelper {
    fun convertDateTimeToMillis(date: String, time: String): Long {
        val dateTimeString = "$date $time"
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        try {
            val dateTime = formatter.parse(dateTimeString)
            return dateTime?.time ?: 0
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return 0
    }

    fun notifHelper(context: Context,time:Long,name:String){
        val currentTimeMillis = System.currentTimeMillis()
        val oneDayInMillis = 24 * 60 * 60 * 1000
        val notificationTimeMillis = time - oneDayInMillis

        if (notificationTimeMillis > currentTimeMillis) {
            val workManager = WorkManager.getInstance(context)

            val inputData = Data.Builder()
                .putLong("acaraTimeMillis", time)
                .putString("acaraName", name)
                .build()

            val notificationRequest =
                OneTimeWorkRequestBuilder<NotifWorker>()
                    .setInputData(inputData)
                    .setInitialDelay(
                        notificationTimeMillis - currentTimeMillis,
                        TimeUnit.MILLISECONDS
                    )
                    .build()

            workManager.enqueue(notificationRequest)
        }
    }
}