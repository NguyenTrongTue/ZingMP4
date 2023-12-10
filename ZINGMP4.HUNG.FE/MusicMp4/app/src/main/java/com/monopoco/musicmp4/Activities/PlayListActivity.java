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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.util.Strings;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.monopoco.musicmp4.Adapters.InsertSongAdapter;
import com.monopoco.musicmp4.Adapters.SearchSongAdapter;
import com.monopoco.musicmp4.Models.PlayListAddSongModel;
import com.monopoco.musicmp4.Models.PlayListModel;
import com.monopoco.musicmp4.Models.SearchModel;
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Requests.APIService;
import com.monopoco.musicmp4.Requests.DataService;
import com.monopoco.musicmp4.Utils.ImageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayListActivity extends AppCompatActivity {

    private PlayListModel playListModel;

    private RecyclerView rclSong;

    private SearchSongAdapter searchSongAdapter;

    private SwipeRefreshLayout refreshLayout;

    private ConstraintLayout btnNewSong;

    private String playListId;

    private Dialog searchSongDialog;

    private ImageView playlistImage;

    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_ios_new_24);
        setSupportActionBar(toolbar);
        playlistImage = findViewById(R.id.playlist_image);
//        playListModel = (PlayListModel) getIntent().getSerializableExtra("playlistInfo");
        if ((String) getIntent().getSerializableExtra("playlistId") != null) {
            playListId = (String) getIntent().getSerializableExtra("playlistId");
        }

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



        FloatingActionButton btnPlayPl = findViewById(R.id.btn_play_pl);

        getDataToRecycleView();


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void updateSearchView (List<SongModel> songModelList, RecyclerView recyclerView) {
        if (recyclerView != null && songModelList != null) {
            InsertSongAdapter insertSongAdapter = new InsertSongAdapter(songModelList, this);
            LinearLayoutManager llm = new LinearLayoutManager(PlayListActivity.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            recyclerView.setAdapter(insertSongAdapter);
        }

    }

    public void addSongToPlaylist( String songId) {
        if (playListId != null) {

            PlayListAddSongModel body = new PlayListAddSongModel(playListId, songId);

            APIService.getService().addSongToPlayList(body).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.code() == 200) {
                        Toast.makeText(PlayListActivity.this, "Add song to playlist successful", Toast.LENGTH_LONG).show();
                        getDataToRecycleView();
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Toast.makeText(PlayListActivity.this, "Có lỗi xảy ra", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void searchSong(String text, RecyclerView recyclerView) {
        SearchModel searchModel = new SearchModel(
                text, 1, 1000
        );
        APIService.getService().SearchSong(searchModel).enqueue(new Callback<List<SongModel>>() {
            @Override
            public void onResponse(Call<List<SongModel>> call, Response<List<SongModel>> response) {
                if (response.code() == 200 && response.body() != null) {
                    updateSearchView(response.body(), recyclerView);
                }
            }

            @Override
            public void onFailure(Call<List<SongModel>> call, Throwable t) {
                Toast.makeText(PlayListActivity.this, "Có lỗi xảy ra", Toast.LENGTH_LONG).show();
            }
        });
    }

    public Dialog createDialog() {
        Dialog dialog = new Dialog(PlayListActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.insert_song_dialog);

        ImageView closeBtn = dialog.findViewById(R.id.insert_song_close);

        EditText searchField = dialog.findViewById(R.id.insert_song_field);
        RecyclerView searchSongRcv = dialog.findViewById(R.id.recycler_insert_song);
        LinearLayoutManager llm = new LinearLayoutManager(PlayListActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        searchSongRcv.setLayoutManager(llm);

        searchField.addTextChangedListener(
                new TextWatcher() {
                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                    private Timer timer = new Timer();
                    private final long DELAY = 500; // Milliseconds

                    @Override
                    public void afterTextChanged(final Editable s) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        if (!Strings.isEmptyOrWhitespace(searchField.getText().toString())) {
                                            // TODO: Do what you need here (refresh list).
                                            Log.e("monopoco", searchField.getText().toString());
                                            searchSong(searchField.getText().toString(), searchSongRcv);
                                        }
                                    }
                                },
                                DELAY
                        );
                    }
                }
        );

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

        if (playListId != null) {
            APIService.getService().GetPlayListById(playListId).enqueue(new Callback<PlayListModel>() {
                @Override
                public void onResponse(Call<PlayListModel> call, Response<PlayListModel> response) {
                    if (response.code() == 200 && response.body() != null) {
                        playListModel = response.body();
                        if (playListModel.getSongModelList() != null) {
                            rclSong.setHasFixedSize(false);
                            searchSongAdapter = new SearchSongAdapter(playListModel.getSongModelList(), PlayListActivity.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PlayListActivity.this, LinearLayoutManager.VERTICAL, false);
                            rclSong.setLayoutManager(linearLayoutManager);
                            rclSong.setAdapter(searchSongAdapter);
                            if (playListModel.getSongModelList().size() > 0) {
                                ImageUtils.setImageUrl(playListModel.getSongModelList().get(0).getImageUrl(), playlistImage, PlayListActivity.this);
                            }
                        }
                    }
                    if (playListModel != null) {
                        collapsingToolbarLayout.setTitle(playListModel.getPlayListName());
                    } else {
                        collapsingToolbarLayout.setTitle("Playlist");
                    }
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<PlayListModel> call, Throwable t) {
                    Toast.makeText(PlayListActivity.this, "Có lỗi xảy ra", Toast.LENGTH_LONG).show();
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                }
            });
        }

    }

    public void dismissDialog() {
        if (searchSongDialog != null && searchSongDialog.isShowing()) {
            searchSongDialog.dismiss();
        }
    }
}