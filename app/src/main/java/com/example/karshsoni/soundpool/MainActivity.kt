package com.example.karshsoni.soundpool

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import javax.xml.transform.Source

class MainActivity : AppCompatActivity() {

    lateinit var soundPool: SoundPool
    var sound1: Int = 0
    var sound2: Int = 0
    var sound3: Int = 0
    var sound4: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            var audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()

            soundPool = SoundPool.Builder()
                    .setMaxStreams(4)
                    .setAudioAttributes(audioAttributes)
                    .build()

        }else{
            soundPool = SoundPool(4, AudioManager.STREAM_MUSIC, 0)
        }

        sound1 = soundPool.load(this, R.raw.s1, 1)
        sound2 = soundPool.load(this, R.raw.s2, 1)
        sound3 = soundPool.load(this, R.raw.s3, 1)
        sound4 = soundPool.load(this, R.raw.s4, 1)

    }

    fun playSound(v: View) {
        when (v.getId()) {
            R.id.button -> {
                soundPool.play(sound1, 1F, 1F, 0, 0, 1F)
                //soundPool.pause(sound3StreamId);
                soundPool.autoPause()
            }
            R.id.button2 -> soundPool.play(sound2, 1F, 1F, 0, 0, 1F)
            R.id.button3 -> soundPool.play(sound3, 1F, 1F, 0, 0, 1F)
            R.id.button4 -> soundPool.play(sound4, 1F, 1F, 0, 0, 1F)
        }
    }

}
