package com.example.karshsoni.soundpool

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.RemoteViews



class ServiceNotification : Service() {

    val NOTIFY_PREVIOUS = "com.tutorialsface.audioplayer.previous"
    val NOTIFY_DELETE = "com.tutorialsface.audioplayer.delete"
    val NOTIFY_PAUSE = "com.tutorialsface.audioplayer.pause"
    val NOTIFY_PLAY = "com.tutorialsface.audioplayer.play"
    val NOTIFY_NEXT = "com.tutorialsface.audioplayer.next"

    lateinit var notificationManager: NotificationManager
    lateinit var notificationchannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId= "com.example.karshsoni.soundpool"
    private val name="testNotification"
    var mediaPlayer = MediaPlayer()

    companion object {
        var count:Int = 0
    }

    var TAG: String = "Count Service"
    var isStopped = false


    override fun onCreate() {

    }

    override fun onDestroy() {
        Log.i(TAG, "On Destroy")
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        mediaPlayer = MediaPlayer.create(this, R.raw.s3)
        var currentPosition = intent!!.extras.getInt("Current Position")
//        var intentX = Intent()
        Log.i(TAG, "OnStartCommand")
        Thread(Runnable {
            Log.i(TAG, "In Thread")
            mediaPlayer.seekTo(currentPosition)
            mediaPlayer.start()
//            shownotification(currentPosition)
            while (!isStopped) {
                mediaPlayer.setOnCompletionListener {
                    isStopped = true
                    stopSelf()
                }
                shownotification()
//                startForeground(channelId, shownotification(currentPosition))


            }
        }).start()

        Log.i(TAG, ": Out thread -> " + count.toString())
        return Service.START_STICKY
    }


    fun setListeners(view: RemoteViews) {
        val previous = Intent(NOTIFY_PREVIOUS)
        val pause = Intent(NOTIFY_PAUSE)
        val next = Intent(NOTIFY_NEXT)

        val pPrevious = PendingIntent.getBroadcast(getApplicationContext(), 0, previous, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btnPrev, pPrevious)

        val pPause = PendingIntent.getBroadcast(getApplicationContext(), 0, pause, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btnPausePlay, pPause)

        val pNext = PendingIntent.getBroadcast(getApplicationContext(), 0, next, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btnNext, pNext)
    }

    @SuppressLint("PrivateResource")
    @RequiresApi(Build.VERSION_CODES.O)

    fun shownotification(){

        val notificationLayout  = RemoteViews(this.packageName, R.layout.status_bar)
        val notificationLayoutExpanded = RemoteViews(this.packageName, R.layout.status_bar_expanded)




        var notificationIntent = Intent(this, Main2Activity::class.java)
        notificationIntent.putExtra("Current Position", mediaPlayer.currentPosition)
        var pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        var notification = NotificationCompat.Builder(this, channelId)
                .setContentTitle("Example Service")
                .setContentText("Working")
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setCustomBigContentView(notificationLayoutExpanded)
                .setContentIntent(pendingIntent)
                .build()


        startForeground(1, notification)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.i(TAG, "On Task Removed")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}
