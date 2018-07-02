package com.example.karshsoni.soundpool

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class App : Application() {

    companion object {
        val CHANNEL_ID = "exampleServiceChannel"
    }


    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var serviceChannel = NotificationChannel(CHANNEL_ID, "Example Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
                    )

            var notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(serviceChannel)
        }
    }

}