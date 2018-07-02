package com.example.karshsoni.soundpool;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ExampleJobService extends JobService {

    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: Job Started.");
        int currentPosition = params.getExtras().getInt("Current Position");
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(ExampleJobService.this, R.raw.s3);
        mediaPlayer.seekTo(currentPosition);
        doBackgroundWork(params, mediaPlayer);
        return true;
    }

    private void doBackgroundWork(final JobParameters params, final MediaPlayer mediaPlayer) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                mediaPlayer.start();
                for(int i =0; i<10;i++){

                    Log.d(TAG, "run: "+i);
                    if(jobCancelled){
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "run: Job Finished.");
                jobFinished(params, false);
            }
        });
        thread.start();

    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob: Job is cancelled before completion.");
        // If our job is not important return false. Otherwise return true for Reschedule.
        jobCancelled  = true;
        return false;
    }
}
