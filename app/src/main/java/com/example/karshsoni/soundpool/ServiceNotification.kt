package com.example.karshsoni.soundpool

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.RemoteViews
import android.app.PendingIntent
import android.content.Context
import android.graphics.Color
import android.widget.SeekBar
import android.widget.Toast
import com.example.karshsoni.soundpool.R.id.seekBar


class ServiceNotification : Service() {

    val NOTIFY_PREVIOUS = "com.example.karshsoni.soundpool.previous"

    val NOTIFY_PAUSE = "com.example.karshsoni.soundpool.pause"

    val NOTIFY_NEXT = "com.example.karshsoni.soundpool.next"

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId= "com.example.karshsoni.soundpool.abc"
    private val name="testNotification"
    var mediaPlayer :MediaPlayer? = null

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

//        mediaPlayer = MediaPlayer.create(this, R.raw.s5)
//        var currentPosition = intent.extras.getInt("Current Position")
//        var intentX = Intent()
        Log.i(TAG, "OnStartCommand")

        Thread(Runnable {
            Log.i(TAG, "In Thread")
            if(intent!!.extras.getString("play") == "Play"){
                if(mediaPlayer == null){
                    mediaPlayer = MediaPlayer.create(this, R.raw.s5)
                }
                mediaPlayer!!.start()
                mediaPlayer!!.setOnCompletionListener(MediaPlayer.OnCompletionListener {
                    isStopped = true
                    if (mediaPlayer != null) {
                        Toast.makeText(this, "In Stop", Toast.LENGTH_SHORT).show()
                        mediaPlayer!!.release()
                        mediaPlayer = null
                        Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            else if(intent.extras.getString("pause") == "Pause"){
                if (mediaPlayer != null) {
                    mediaPlayer!!.pause()
                }
            }else if(intent.extras.getString("stop") == "Stop"){
                if(mediaPlayer!=null){
                    mediaPlayer!!.release()
                    mediaPlayer = null
                }
            }else if(intent.extras.getString("PAUSE") == "Pause"){
                if (mediaPlayer != null) {
                    mediaPlayer!!.pause()
                }
            }else if(intent.extras.getString("PLAY") == "Play"){
                if(mediaPlayer == null){
                    mediaPlayer = MediaPlayer.create(this, R.raw.s5)
                }
                mediaPlayer!!.start()
                mediaPlayer!!.setOnCompletionListener(MediaPlayer.OnCompletionListener {
                    isStopped = true
                    if (mediaPlayer != null) {
                        Toast.makeText(this, "In Stop", Toast.LENGTH_SHORT).show()
                        mediaPlayer!!.release()
                        mediaPlayer = null
                        Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            shownotification()
        }).start()

        Log.i(TAG, ": Out thread -> " + count.toString())
        return Service.START_STICKY
    }



    fun setListeners(view: RemoteViews, context: Context) {
        val previous = Intent(NOTIFY_PREVIOUS)
        val pause = Intent(NOTIFY_PAUSE)
        val next = Intent(NOTIFY_NEXT)

//        Toast.makeText(context, "tappedSetListyenre", Toast.LENGTH_SHORT).show()
//        previous.putExtra("DO", "Previous")
        val bundle  = Bundle()

//        previous.putExtra("Media", mediaPlayer!!.currentPosition)
        val pPrevious = PendingIntent.getBroadcast(context, 0, previous, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btnPrev, pPrevious)
//
//        val play = Intent(this, BroadcastReceiverNotification::class.java)
//        play.putExtra("DO", "Play")
//        pause.putExtra("DO", "Pause")
//        pause.putExtra("Media", mediaPlayer!!.currentPosition)
        val pPause = PendingIntent.getBroadcast(context, 0, pause, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btnPausePlay, pPause)

//        val nextI = Intent(this, BroadcastReceiverNotification::class.java)
//        nextI.putExtra("DO", "Next")
//        next.putExtra("DO", "Next")
//        next.putExtra("Media", mediaPlayer!!.currentPosition)
        val pNext = PendingIntent.getBroadcast(context, 0, next, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btnNext, pNext)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun shownotification(){

        val notificationLayout  = RemoteViews(this.packageName, R.layout.status_bar)
//        val notificationLayoutExpanded = RemoteViews(this.packageName, R.layout.status_bar_expanded)

        var notificationIntent = Intent(this, Main2Activity::class.java)
//        notificationIntent.putExtra("Current Position", mediaPlayer!!.currentPosition)
        var pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

//        notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        notificationChannel = NotificationChannel(channelId, "Notification", NotificationManager.IMPORTANCE_HIGH)
//        notificationChannel.enableLights(true)
//        notificationChannel.lightColor = Color.BLUE
//        notificationChannel.enableVibration(false)
//        notificationManager.createNotificationChannel(notificationChannel)

        var notification = NotificationCompat.Builder(this, channelId)
                .setContentTitle("Example Service")
                .setContentText("Working")
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
//                .setCustomBigContentView(notificationLayoutExpanded)
                .setContentIntent(pendingIntent)
                .build()

        setListeners(notificationLayout, this)

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
