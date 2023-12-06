package com.monopoco.musicmp4.Services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;

import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MediaPlayerService extends Service {

    private MediaPlayer mediaPlayer;

    private final IBinder binder = new LocalBinder();

    private int currentIndexPlaying = 0;

    private SongModel currentSong;

    private ArrayList<SongModel> listSong;

    @Override
    public void onCreate() {
        super.onCreate();
        listSong = new ArrayList<>();
        currentIndexPlaying = 0;
        Log.e("monopoco", "Creating Service");
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public MediaPlayerService getService() {
            // Return this instance of LocalService so clients can call public methods.
            return MediaPlayerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (listSong.size() > 0) {
                listSong.removeAll(listSong.subList(currentIndexPlaying + 1, listSong.size()));
            }
            listSong.addAll((ArrayList<SongModel>) bundle.get("songs"));
            currentSong = listSong.get(currentIndexPlaying);
            if (listSong != null && listSong.size() > 0) {
                startMusic(currentSong);
            }
        }
        return START_NOT_STICKY;
    }

    private void startMusic(SongModel songModel) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), songModel.getResource());
        } else {
            mediaPlayer.reset();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), songModel.getResource());
        }
        mediaPlayer.start();
    }

    private void playOrPauseMusic() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                Log.e("monopoco", mediaPlayer.toString());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        Log.e("monopoco", "Destroy Service");

    }

    public void nextSong() {
        mediaPlayer.reset();
        if (currentIndexPlaying < listSong.size() - 1) {
            currentIndexPlaying += 1;
            startMusic(listSong.get(currentIndexPlaying));
        } else {
            listSong.add(SongModel.allSongFake.get(new Random().nextInt(SongModel.allSongFake.size())));
            currentIndexPlaying += 1;
            startMusic(listSong.get(currentIndexPlaying));
        }
    }

    public void preSong() {
        mediaPlayer.reset();
        if (currentIndexPlaying != 0) {
            currentIndexPlaying -= 1;
            startMusic(listSong.get(currentIndexPlaying));
        } else {
            listSong.add(0, SongModel.allSongFake.get(new Random().nextInt(SongModel.allSongFake.size())));
            startMusic(listSong.get(currentIndexPlaying));
        }
    }


    public int getCurrentIndexPlaying() {
        return currentIndexPlaying;
    }

    public void setCurrentIndexPlaying(int currentIndexPlaying) {
        this.currentIndexPlaying = currentIndexPlaying;
    }

    public SongModel getCurrentSong() {
        return listSong.get(currentIndexPlaying);
    }

    public void setCurrentSong(SongModel currentSong) {
        this.currentSong = currentSong;
    }
}