package com.example.karshsoni.soundpool;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Objects;

public class Main2Activity extends AppCompatActivity {

    Button play, pause, stop;
    MediaPlayer sound;
    SeekBar seekBar;
    private Handler mSeekbarUpdateHandler;
    private static final String TAG = "Main2Activity";
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.tutorialsface.audioplayer.previous");
        intentFilter.addAction("com.tutorialsface.audioplayer.pause");
        intentFilter.addAction("com.tutorialsface.audioplayer.next");

        registerReceiver(broadcastReceiver, intentFilter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mSeekbarUpdateHandler = new Handler();

        play = findViewById(R.id.btnPlay);
        pause = findViewById(R.id.btnPause);
        stop = findViewById(R.id.btnStop);

        seekBar = findViewById(R.id.seekBar);
        broadcastReceiver = new BroadcastReceiverNotification();

        Intent intent = new Intent();
        intent.setAction("my.custom.action.tag.fortedemo");
        intent.setAction("com.tutorialsface.audioplayer.previous");
        intent.setAction("com.tutorialsface.audioplayer.pause");
        intent.setAction("com.tutorialsface.audioplayer.next");
        sendBroadcast(intent);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    sound.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentPlay = new Intent(Main2Activity.this, ServiceNotification.class);
                intentPlay.putExtra("play", "Play");
                startService(intentPlay);
                if(sound == null){
                    sound = MediaPlayer.create(Main2Activity.this, R.raw.s5);
                    sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlayer();
                        }
                    });
                }
                sound.start();
                seekBar.setMax(sound.getDuration());
                mUpdateSeekbar.run();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound != null){
                    sound.pause();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
                stopPlayer();
            }
        });


    }

    private Runnable mUpdateSeekbar = new Runnable() {
        @Override
        public void run() {
            seekBar.setProgress(sound.getCurrentPosition());
            mSeekbarUpdateHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onStop() {
        super.onStop();

        Intent intent = new Intent(this, ServiceNotification.class);
        intent.putExtra("Current Position", sound.getCurrentPosition());
        sound.release();
//        ContextCompat.startForegroundService(this, intent);
        startService(intent);

    }

    private void stopPlayer() {
        if (sound != null) {
            sound.release();
            sound = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }

}
