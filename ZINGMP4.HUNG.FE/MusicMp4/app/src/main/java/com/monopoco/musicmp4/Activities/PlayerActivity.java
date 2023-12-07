package com.monopoco.musicmp4.Activities;

import static com.monopoco.musicmp4.Classes.ApplicationClass.ACTION_PLAY;
import static com.monopoco.musicmp4.Classes.ApplicationClass.ACTION_PREVIOUS;
import static com.monopoco.musicmp4.Classes.ApplicationClass.CHANNEL_ID_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.monopoco.musicmp4.Fragments.HomeFragment;
import com.monopoco.musicmp4.Fragments.MusicPlayerFragment;
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Receiver.NotificationReceiver;
import com.monopoco.musicmp4.Services.MediaPlayerService;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity {

    private FrameLayout frameLayout;

    private ArrayList<SongModel> songModels;

    private MediaPlayerService mediaPlayerService;

    private MediaSessionCompat mediaSessionCompat;

    private MusicPlayerFragment playerFragment;

    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        mediaSessionCompat = new MediaSessionCompat(getBaseContext(), "My Audio");


        Toolbar toolbar = findViewById(R.id.toolbar_player);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_ios_new_24);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Playing now");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        songModels = (ArrayList<SongModel>) getIntent().getSerializableExtra("songsInfo");
        frameLayout = findViewById(R.id.player_frame_layout);
        startService();
        Intent intent = new Intent(this, MediaPlayerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void setFragment(Fragment fragment) {
        if (fragment instanceof MusicPlayerFragment) {
            this.playerFragment = (MusicPlayerFragment) fragment;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(frameLayout.getId(), this.playerFragment);
            fragmentTransaction.commit();
        }
    }

    public void startService() {
        Intent intent = new Intent(this, MediaPlayerService.class);
        intent.putExtra("songs", songModels);
        startService(intent);
    }

    /**
     * Defines callbacks for service binding, passed to bindService().
     */
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance.
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            mediaPlayerService = binder.getService();
            mediaPlayerService.setCallBack(PlayerActivity.this);
            mBound = true;
            boolean isPlaying = false;
            if (mediaPlayerService.getMediaPlayer().isPlaying()) {
                isPlaying = true;
            }
            setFragment(new MusicPlayerFragment(mediaPlayerService.getCurrentSong(), isPlaying));
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    public MediaPlayerService getMediaPlayerService() {
        return mediaPlayerService;
    }

    public void playOrPauseClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (mediaPlayerService != null && mediaPlayerService.getMediaPlayer() != null) {
                if (mediaPlayerService.getMediaPlayer().isPlaying()) {
                    onPauseClick(playerFragment.getPlayOrPauseButton());
                } else {
                    onPlayClick(playerFragment.getPlayOrPauseButton());
                }
            }
        }
    }

    private void onPauseClick(View playOrPauseButton) {
        mediaPlayerService.getMediaPlayer().pause();
        ((ImageView) playOrPauseButton).setImageResource(R.drawable.ic_play);
        showNotification(R.drawable.ic_play);
    }

    private void onPlayClick(View playOrPauseButton) {
        mediaPlayerService.getMediaPlayer().start();
        ((ImageView) playOrPauseButton).setImageResource(R.drawable.ic_pause);
        showNotification(R.drawable.ic_pause);
    }

    public void onSkipFwd() {
        mediaPlayerService.nextSong();
        setFragment(new MusicPlayerFragment(mediaPlayerService.getCurrentSong(), true));
        showNotification(R.drawable.ic_pause);
    }

    public void onSkipBack() {
        mediaPlayerService.preSong();
        setFragment(new MusicPlayerFragment(mediaPlayerService.getCurrentSong(), true));
        showNotification(R.drawable.ic_pause);
    }

    public void onRepeatClick(View repeatBtn) {
        if (mediaPlayerService.getRepeat()) {
            mediaPlayerService.setRepeat(false);
            ((ImageView)repeatBtn).setImageResource(R.drawable.ic_repeat);
        } else {
            mediaPlayerService.setRepeat(true);
            ((ImageView)repeatBtn).setImageResource(R.drawable.ic_repeat_active);
        }
    }

    public void showNotification(int playOrPauseBtn) {
        Intent intent = new Intent(this, PlayerActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Intent prevIntent = new Intent(this, NotificationReceiver.class)
                .setAction(ACTION_PREVIOUS);
        PendingIntent prevPending = PendingIntent.getBroadcast(this, 0, prevIntent, PendingIntent.FLAG_IMMUTABLE);

        Intent pauseIntent = new Intent(this, NotificationReceiver.class)
                .setAction(ACTION_PLAY);
        PendingIntent pausePending = PendingIntent.getBroadcast(this, 0, pauseIntent, PendingIntent.FLAG_IMMUTABLE);

        Intent nextIntent = new Intent(this, NotificationReceiver.class)
                .setAction(ACTION_PREVIOUS);
        PendingIntent nextPending = PendingIntent.getBroadcast(this, 0, nextIntent, PendingIntent.FLAG_IMMUTABLE);

        byte[] picture = null;

//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), mediaPlayerService.getCurrentSong().getImageUrl());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        picture = stream.toByteArray();

        Bitmap thumb = null;
        if (picture != null) {
            thumb = BitmapFactory.decodeByteArray(picture, 0, picture.length);
        } else {
            thumb = BitmapFactory.decodeResource(getResources(), R.drawable.queen1);
        }

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_2)
                .setSmallIcon(playOrPauseBtn)
                .setLargeIcon(thumb)
                .setContentTitle(mediaPlayerService.getCurrentSong().getSongName())
                .setContentText(mediaPlayerService.getCurrentSong().getSinger())
                .addAction(R.drawable.ic_skip_back, "Previous", prevPending)
                .addAction(playOrPauseBtn, "Pause", pausePending)
                .addAction(R.drawable.ic_skip_fwd, "Next", nextPending)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSessionCompat.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOnlyAlertOnce(true)
                .setNotificationSilent()
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}