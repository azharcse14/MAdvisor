package com.azhar.m_advisor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MediaPlayerActivity<sc> extends AppCompatActivity {

    TextView currentDurationTv, totalDurationTv;
    ProgressBar playerSeekBar;

    private Handler handler = new Handler();
    Button homeBtn;
    ImageView speakerImg, goToHomeImg;
    TextView messageTv;
    MediaPlayer mediaPlayer;
    ProgressDialog progressDialog;
    private boolean playPause;
    private boolean initialStage = true;
    String name, uri, schedule, startTime;
    int ts, sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        currentDurationTv = findViewById(R.id.txtCurrentDurationId);
        totalDurationTv = findViewById(R.id.totalDurationTvId);
        playerSeekBar = findViewById(R.id.playerSeekBarId);

        messageTv = findViewById(R.id.messageTvId);
        speakerImg = findViewById(R.id.speakerImgId);
        homeBtn = findViewById(R.id.homeBtnId);
        goToHomeImg = findViewById(R.id.goToHome);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        playerSeekBar.setMax(100);

        progressDialog = new ProgressDialog(this);

        name = getIntent().getStringExtra("name");
        uri = getIntent().getStringExtra("URI");
        schedule = getIntent().getStringExtra("Schedule");
        startTime = getIntent().getStringExtra("Start_Time");

        ts = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        sc = Integer.parseInt(schedule);


        if (!playPause) {

            if (initialStage) {
                new player().execute(uri);
                messageTv.setText("On Going");
                homeBtn.setVisibility(View.GONE);

            } else {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    updateSeekBar();
                }
            }
            playPause = true;

        } else {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
            playPause = false;

        }


        //=================================================================================
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        //=================================================================================
        if (sc==0){
            goToHomeImg.setVisibility(View.VISIBLE);
        }else {
            goToHomeImg.setVisibility(View.GONE);
        }
        //=================================================================================
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            currentDurationTv.setText(milliSecondToTimer(currentDuration));
        }
    };

    private void updateSeekBar() {
        if (mediaPlayer.isPlaying()) {
            playerSeekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
            handler.postDelayed(updater, 1000);
        }
    }


    private String milliSecondToTimer(long miliSecond) {
        String timerString = "";
        String secondString = "";

        int hours = (int) (miliSecond / (1000 * 60 * 60));
        int minutes = (int) ((miliSecond % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) ((miliSecond % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if (hours > 0) {
            timerString = hours + ":";
        }

        if (seconds < 10) {
            secondString = "0" + seconds;
        } else {
            secondString = "" + seconds;
        }

        timerString = timerString + minutes + ":" + secondString;
        return timerString;

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer.pause();
    }

    class player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            boolean prepared = false;

            try {
                mediaPlayer.setDataSource(strings[0]);

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        initialStage = true;
                        playPause = false;
                        messageTv.setText("End");
                        speakerImg.setVisibility(View.GONE);
                        homeBtn.setVisibility(View.VISIBLE);
                        mp.stop();
                        mp.reset();
                    }
                });

                mediaPlayer.prepare();
                prepared = true;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }
            mediaPlayer.start();
            mediaPlayer.seekTo((ts - sc) * 1000);
            initialStage = false;


            //=======================================================
            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            currentDurationTv.setText(milliSecondToTimer(currentDuration));
            //=======================================================
            totalDurationTv.setText(milliSecondToTimer(mediaPlayer.getDuration()));

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Buffaring.......");
            progressDialog.show();
        }
    }

    public void goToHome(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Please complete the audio", Toast.LENGTH_LONG).show();
    }
}