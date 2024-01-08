package com.monopoco.musicmp4.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.monopoco.musicmp4.Requests.APIService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikedSongFragment extends Fragment {

    private GridView likedSongGridView;

    private List<SongModel> songModelList;

    private SwipeRefreshLayout likedReload;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_liked_song, container, false);
        likedSongGridView = view.findViewById(R.id.like_song_grid_view);
//        songModelList = SongModel.songModelList4;

        likedReload = view.findViewById(R.id.liked_reload);
        likedReload.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        getData();

        return view;
    }

    private void getData() {
        SharedPreferences sp = getContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String userId = sp.getString("userId", null);
        APIService.getService().GetLikedSongByUser(userId).enqueue(new Callback<List<SongModel>>() {
            @Override
            public void onResponse(Call<List<SongModel>> call, Response<List<SongModel>> response) {
                if (response.code() == 200) {
                    songModelList = response.body();
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
                }
                likedReload.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<SongModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Có lỗi xảy ra", Toast.LENGTH_LONG).show();
                likedReload.setRefreshing(false);
            }
        });
    }
}