package com.monopoco.musicmp4.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.monopoco.musicmp4.Adapters.SearchSongAdapter;
import com.monopoco.musicmp4.Models.SearchModel;
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Requests.APIService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {

    private RecyclerView rclSearchSong;

    private SearchSongAdapter searchSongAdapter;

    private EditText searchField;

    private SwipeRefreshLayout searchReload;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        rclSearchSong = view.findViewById(R.id.rcl_search_song);

        searchField = view.findViewById(R.id.search_field);

        searchReload = view.findViewById(R.id.search_reload);

        searchReload.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();

            }
        });

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
                                        // TODO: Do what you need here (refresh list).
                                        Log.e("monopoco", searchField.getText().toString());
                                        if (!searchField.getText().toString().isEmpty()) {
                                            getData();
                                        }
                                    }
                                },
                                DELAY
                        );
                    }
                }
        );

        // Data
        ArrayList<SongModel> songModelArrayList = new ArrayList<>();

        rclSearchSong.setHasFixedSize(true);

        searchSongAdapter = new SearchSongAdapter(songModelArrayList, getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rclSearchSong.setLayoutManager(linearLayoutManager);
        rclSearchSong.setAdapter(searchSongAdapter);
        return view;
    }

    private void getData() {
        String textSearch = searchField.getText().toString();
        Integer page = 1;
        Integer size = 100;
        SearchModel searchModel = new SearchModel(textSearch, page, size);


        APIService.getService().SearchSong(searchModel).enqueue(new Callback<List<SongModel>>() {
            @Override
            public void onResponse(Call<List<SongModel>> call, Response<List<SongModel>> response) {
                searchSongAdapter.setSongModelList(response.body());
                rclSearchSong.setAdapter(searchSongAdapter);
                if (searchReload.isRefreshing()) {
                    searchReload.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<SongModel>> call, Throwable t) {
                if (searchReload.isRefreshing()) {
                    searchReload.setRefreshing(false);
                }
                Toast.makeText(getContext(), "Không tìm thấy bài hát nào", Toast.LENGTH_LONG).show();
            }
        });


    }


}