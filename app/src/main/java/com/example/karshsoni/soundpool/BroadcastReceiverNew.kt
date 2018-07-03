package com.example.karshsoni.soundpool

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.PendingIntent
import android.media.MediaPlayer
import android.provider.MediaStore
import android.widget.RemoteViews
import android.widget.Toast


class BroadcastReceiverNotification: BroadcastReceiver() {
    var intentExtra = Intent()
    override fun onReceive(context: Context?, intent: Intent?) {

//        Toast.makeText(context, "tapped", Toast.LENGTH_SHORT).show()
        val NOTIFY_PREVIOUS = "com.tutorialsface.audioplayer.previous"
        val NOTIFY_DELETE = "com.tutorialsface.audioplayer.delete"
        val NOTIFY_PAUSE = "com.tutorialsface.audioplayer.pause"
        val NOTIFY_PLAY = "com.tutorialsface.audioplayer.play"
        val NOTIFY_NEXT = "com.tutorialsface.audioplayer.next"
        var action: String? = " "

        val mediaPlayer = MediaPlayer.create(context, R.raw.s5)
        mediaPlayer.seekTo(intent!!.extras.getInt("Media"))
        mediaPlayer.start()
        if(intent.action == NOTIFY_PAUSE){
            intentExtra.putExtra("PAUSE", "Pause")
            context!!.startService(intentExtra)
            Toast.makeText(context, "tappedPause", Toast.LENGTH_SHORT).show()
        }else if(intent.action == NOTIFY_PREVIOUS){
            val ints = intent.extras.get("Media")
            Toast.makeText(context, "tappedPrevious"+ints, Toast.LENGTH_SHORT).show()
        }else if(intent.action == NOTIFY_NEXT){
            Toast.makeText(context, "tappedNext", Toast.LENGTH_SHORT).show()
        }
//

    }

}