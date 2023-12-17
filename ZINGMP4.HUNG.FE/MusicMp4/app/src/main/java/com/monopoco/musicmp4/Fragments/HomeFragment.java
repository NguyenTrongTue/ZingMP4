package com.monopoco.musicmp4.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.monopoco.musicmp4.Activities.PlayListActivity;
import com.monopoco.musicmp4.Activities.PlayerActivity;
import com.monopoco.musicmp4.Models.PlayListModel;
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Requests.APIService;
import com.monopoco.musicmp4.Utils.ImageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private List<SongModel> songModels = new ArrayList<>();

    private List<PlayListModel> playListModels = new ArrayList<>();

    private LinearLayout linearRecommended;

    private LinearLayout linearPlaylist;

    private final List<String> impressiveId = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        songModels = SongModel.songModelList1;
//        getDataSong();
        playListModels = new ArrayList<>();

    }

    private void getDataSong() {
        APIService.getService().GetPlaylistCurrentDay().enqueue(new Callback<List<SongModel>>() {
            @Override
            public void onResponse(Call<List<SongModel>> call, Response<List<SongModel>> response) {
                Log.e("monopoco", response.body().toString());
                songModels = response.body();
                linearRecommended = getView().findViewById(R.id.linear_recommended);
                songModels.forEach(songModel -> {
                    View viewItem = getLayoutInflater().inflate(R.layout.recomment_slide_item, null);
                    viewItem.findViewById(R.id.item_view).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), PlayerActivity.class);
                            intent.putExtra("songsInfo", new ArrayList<SongModel>(Arrays.asList(songModel)));
                            startActivity(intent);
                        }
                    });

                    ImageView slideImage = viewItem.findViewById(R.id.image_song);
                    try {
                        ImageUtils.setImageUrl(songModel.getImageUrl(), slideImage, getContext());
                    } catch (Exception exception) {
                        slideImage.setImageResource(R.drawable.queen1);
                    }
                    TextView songName = viewItem.findViewById(R.id.song_name);
                    songName.setText(songModel.getSongName());
                    TextView singerName = viewItem.findViewById(R.id.singer_name);
                    singerName.setText(songModel.getSinger());
                    if (songModels.indexOf(songModel) == 0) {
                        LinearLayout ll = new LinearLayout(getContext());
                        ll.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(70, 0, 0, 0);
                        linearRecommended.addView(viewItem, layoutParams);
                    } else if (songModels.indexOf(songModel) == songModels.size() - 1) {
                        LinearLayout ll = new LinearLayout(getContext());
                        ll.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 0, 70, 0);
                        linearRecommended.addView(viewItem, layoutParams);
                    } else {
                        linearRecommended.addView(viewItem);
                    }
                });

                linearPlaylist = getView().findViewById(R.id.linear_play_list);

                impressiveId.forEach(playlistId -> {
                    APIService.getService().GetPlayListById(playlistId).enqueue(new Callback<PlayListModel>() {
                        @Override
                        public void onResponse(Call<PlayListModel> call, Response<PlayListModel> response) {
                            if (response.code() == 200) {
                                View viewItem = getLayoutInflater().inflate(R.layout.play_list_item, null);
                                viewItem.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getActivity(), PlayListActivity.class);
                                        intent.putExtra("playlistId",playlistId );
                                        startActivity(intent);
                                    }
                                });

                                ImageView playlistImage = viewItem.findViewById(R.id.play_list_image);
                                ImageUtils.setImageUrl(response.body().getPlaylistImage(), playlistImage, getContext());
                                TextView playListName = viewItem.findViewById(R.id.play_list_name);
                                playListName.setText(response.body().getPlayListName());
                                if (linearPlaylist.getChildCount() == 0) {
                                    LinearLayout ll = new LinearLayout(getContext());
                                    ll.setOrientation(LinearLayout.VERTICAL);
                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    layoutParams.setMargins(70, 0, 0, 0);
                                    linearPlaylist.addView(viewItem, layoutParams);
                                } else if (linearPlaylist.getChildCount() == impressiveId.size() - 1) {
                                    LinearLayout ll = new LinearLayout(getContext());
                                    ll.setOrientation(LinearLayout.VERTICAL);
                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    layoutParams.setMargins(0, 0, 70, 0);
                                    linearPlaylist.addView(viewItem, layoutParams);
                                } else {
                                    linearPlaylist.addView(viewItem);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<PlayListModel> call, Throwable t) {

                        }
                    });

                });
            }

            @Override
            public void onFailure(Call<List<SongModel>> call, Throwable t) {
                t.printStackTrace();
                Log.e("monopoco", "error");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        impressiveId.add("7d669019-dde3-44d8-a506-6881e3fb9ad8");
        impressiveId.add("3ca6dacc-1c62-4ffa-857e-5a10822f63db");
        impressiveId.add("a4f22c6c-ae80-4d1b-a673-f923f018cfd6");
        impressiveId.add("e7ef7bfa-6d32-41a7-959f-4393b491b381");
        impressiveId.add("8737a205-8ccc-4ab4-b890-5472c0c5a3a9");


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getDataSong();


        return view;
    }
}