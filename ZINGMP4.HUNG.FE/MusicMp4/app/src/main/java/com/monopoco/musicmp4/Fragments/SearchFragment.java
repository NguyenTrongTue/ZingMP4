package com.monopoco.musicmp4.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.monopoco.musicmp4.Adapters.SearchSongAdapter;
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class SearchFragment extends Fragment {

    private RecyclerView rclSearchSong;

    private SearchSongAdapter searchSongAdapter;

    private EditText searchField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        rclSearchSong = view.findViewById(R.id.rcl_search_song);

        searchField = view.findViewById(R.id.search_field);

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
                                    }
                                },
                                DELAY
                        );
                    }
                }
        );

        // Data
        ArrayList<SongModel> songModelArrayList = new ArrayList<>();
        songModelArrayList.add(SongModel.song1);
        songModelArrayList.add(SongModel.song2);
        songModelArrayList.add(SongModel.song3);
        songModelArrayList.add(SongModel.song4);
        songModelArrayList.add(SongModel.song5);
        songModelArrayList.add(SongModel.song6);
        songModelArrayList.add(SongModel.song7);
        songModelArrayList.add(SongModel.song8);
        songModelArrayList.add(SongModel.song9);
        songModelArrayList.add(SongModel.song10);
        songModelArrayList.add(SongModel.song11);
        songModelArrayList.add(SongModel.song12);

        rclSearchSong.setHasFixedSize(true);

        searchSongAdapter = new SearchSongAdapter(songModelArrayList, getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rclSearchSong.setLayoutManager(linearLayoutManager);
        rclSearchSong.setAdapter(searchSongAdapter);
        return view;
    }


}