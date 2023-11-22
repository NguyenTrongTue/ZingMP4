package com.monopoco.musicmp4.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.monopoco.musicmp4.Fragments.HomeFragment;
import com.monopoco.musicmp4.Fragments.MusicPlayerFragment;
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Services.MediaPlayerService;

public class PlayerActivity extends AppCompatActivity {

    private FrameLayout frameLayout;

    private SongModel songModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);


        Toolbar toolbar = findViewById(R.id.toolbar_player);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_ios_new_24);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Playing now");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        if (mediaPlayer.isPlaying()) {
//            mediaPlayer.reset();
//        }

//        mediaPlayer = MediaPlayer.create(this, R.raw.silent_night);
        songModel = (SongModel) getIntent().getSerializableExtra("songInfo");
        frameLayout = findViewById(R.id.player_frame_layout);
        setFragment(new MusicPlayerFragment(songModel));
        startService();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    public void startService() {
        Intent intent = new Intent(this, MediaPlayerService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("song", songModel);
        intent.putExtras(bundle);
        startService(intent);
    }


}