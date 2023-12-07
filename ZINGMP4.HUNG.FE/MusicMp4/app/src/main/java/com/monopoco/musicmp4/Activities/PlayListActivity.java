package com.monopoco.musicmp4.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.monopoco.musicmp4.Adapters.InsertSongAdapter;
import com.monopoco.musicmp4.Adapters.SearchSongAdapter;
import com.monopoco.musicmp4.Models.PlayListModel;
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.R;

import java.util.ArrayList;

public class PlayListActivity extends AppCompatActivity {

    private PlayListModel playListModel;

    private RecyclerView rclSong;

    private SearchSongAdapter searchSongAdapter;

    private SwipeRefreshLayout refreshLayout;

    private ConstraintLayout btnNewSong;

    private Dialog searchSongDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_ios_new_24);
        setSupportActionBar(toolbar);
        playListModel = (PlayListModel) getIntent().getSerializableExtra("playlistInfo");

        searchSongDialog = createDialog();


        refreshLayout = findViewById(R.id.swipe_refresh);
        btnNewSong = findViewById(R.id.item_add_song);
        btnNewSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSongDialog.show();
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataToRecycleView();
                refreshLayout.setRefreshing(false);
            }
        });

        rclSong = findViewById(R.id.rcl_song);

        ImageView playListImage = findViewById(R.id.playlist_image);


        FloatingActionButton btnPlayPl = findViewById(R.id.btn_play_pl);

        if (playListModel != null) {
            collapsingToolbarLayout.setTitle(playListModel.getPlayListName());
            playListImage.setImageResource(playListModel.getImage());
            getDataToRecycleView();
        } else {
            collapsingToolbarLayout.setTitle("Playlist");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public Dialog createDialog() {
        Dialog dialog = new Dialog(PlayListActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.insert_song_dialog);

        ImageView closeBtn = dialog.findViewById(R.id.insert_song_close);

        RecyclerView searchSongRcv = dialog.findViewById(R.id.recycler_insert_song);
        LinearLayoutManager llm = new LinearLayoutManager(PlayListActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        searchSongRcv.setLayoutManager(llm);

        InsertSongAdapter insertSongAdapter = new InsertSongAdapter(new ArrayList<>(), PlayListActivity.this);

        searchSongRcv.setAdapter(insertSongAdapter);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        return dialog;
    }

    public void getDataToRecycleView() {
        rclSong.setHasFixedSize(false);

        searchSongAdapter = new SearchSongAdapter(playListModel.getSongModelList(), PlayListActivity.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PlayListActivity.this, LinearLayoutManager.VERTICAL, false);
        rclSong.setLayoutManager(linearLayoutManager);
        rclSong.setAdapter(searchSongAdapter);
    }

    public void addSongToPlaylist(SongModel songModel) {
        this.playListModel.getSongModelList().add(songModel);
        Toast.makeText(PlayListActivity.this, String.format("Added to %s.", playListModel.getPlayListName()), Toast.LENGTH_SHORT);
        getDataToRecycleView();

    }

    public void dismissDialog() {
        if (searchSongDialog != null && searchSongDialog.isShowing()) {
            searchSongDialog.dismiss();
        }
    }
}