package com.monopoco.musicmp4.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.monopoco.musicmp4.Activities.PlayerActivity;
import com.monopoco.musicmp4.Adapters.GridAdapter;
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LikedSongFragment extends Fragment {

    private GridView likedSongGridView;

    private List<SongModel> songModelList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_liked_song, container, false);
        songModelList = SongModel.songModelList4;
        likedSongGridView = view.findViewById(R.id.like_song_grid_view);
        GridAdapter gridAdapter = new GridAdapter(songModelList, getContext());
        likedSongGridView.setAdapter(gridAdapter);
        likedSongGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PlayerActivity.class);
                intent.putExtra("songsInfo", new ArrayList<SongModel>(Arrays.asList(songModelList.get(position))));
                startActivity(intent);
            }
        });
        return view;
    }
}