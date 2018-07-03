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
    lateinit var intentExtra:Intent
    override fun onReceive(context: Context?, intent: Intent?) {

//        Toast.makeText(context, "tapped", Toast.LENGTH_SHORT).show()
        val NOTIFY_PREVIOUS = "com.example.karshsoni.soundpool.previous"
        val NOTIFY_PAUSE = "com.example.karshsoni.soundpool.pause"
        var action: String? = " "

        if(intent!!.action == NOTIFY_PAUSE){
            intentExtra = Intent(context, ServiceNotification::class.java)
            intentExtra.putExtra("PAUSE", "Pause")
            context!!.startService(intentExtra)
            Toast.makeText(context, "tappedPause", Toast.LENGTH_SHORT).show()
        }else if(intent.action == NOTIFY_PREVIOUS){
            intentExtra = Intent(context, ServiceNotification::class.java)
            intentExtra.putExtra("PLAY", "Play")
            context!!.startService(intentExtra)
            Toast.makeText(context, "tappedPrevious", Toast.LENGTH_SHORT).show()
        }
//

    }

}