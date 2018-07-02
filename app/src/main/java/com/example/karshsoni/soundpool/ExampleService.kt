package com.example.karshsoni.soundpool

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import com.example.karshsoni.soundpool.App.Companion.CHANNEL_ID

class ExampleService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var input = intent!!.extras.getString("Name")

        var notificationIntent = Intent(this, Main3Activity::class.java)
        var pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        var notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build()

        startForeground(1, notification)

        return START_NOT_STICKY

    }

}