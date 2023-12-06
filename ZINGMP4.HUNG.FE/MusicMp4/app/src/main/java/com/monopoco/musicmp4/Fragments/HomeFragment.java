package com.monopoco.musicmp4.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monopoco.musicmp4.Activities.PlayListActivity;
import com.monopoco.musicmp4.Activities.PlayerActivity;
import com.monopoco.musicmp4.Models.PlayListModel;
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment {

    private List<SongModel> songModels = new ArrayList<>();

    private List<PlayListModel> playListModels = new ArrayList<>();

    private LinearLayout linearRecommended;

    private LinearLayout linearPlaylist;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songModels = SongModel.songModelList1;

        playListModels = PlayListModel.playListModelList;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        linearRecommended = view.findViewById(R.id.linear_recommended);
        songModels.forEach(songModel -> {
            View viewItem = inflater.inflate(R.layout.recomment_slide_item, null);
            viewItem.findViewById(R.id.item_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PlayerActivity.class);
                    intent.putExtra("songsInfo", new ArrayList<SongModel>(Arrays.asList(songModel)));
                    startActivity(intent);
                }
            });
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(getActivity(), PlayerActivity.class);
//                    intent.putExtra("songInfo", songModel);
//                    startActivity(intent);
//                }
//            });
            ImageView slideImage = viewItem.findViewById(R.id.image_song);
            slideImage.setImageResource(songModel.getImage());
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

        linearPlaylist = view.findViewById(R.id.linear_play_list);

        playListModels.forEach(playListModel -> {
            View viewItem = inflater.inflate(R.layout.play_list_item, null);
            viewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PlayListActivity.class);
                    intent.putExtra("playlistInfo", playListModel);
                    startActivity(intent);
                }
            });

            ImageView playlistImage = viewItem.findViewById(R.id.play_list_image);
            playlistImage.setImageResource(playListModel.getImage());
            TextView playListName = viewItem.findViewById(R.id.play_list_name);
            playListName.setText(playListModel.getPlayListName());
            if (playListModels.indexOf(playListModel) == 0) {
                LinearLayout ll = new LinearLayout(getContext());
                ll.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(70, 0, 0, 0);
                linearPlaylist.addView(viewItem, layoutParams);
            } else if (playListModels.indexOf(playListModel) == playListModels.size() - 1) {
                LinearLayout ll = new LinearLayout(getContext());
                ll.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 70, 0);
                linearPlaylist.addView(viewItem, layoutParams);
            } else {
                linearPlaylist.addView(viewItem);
            }
        });

        return view;
    }
}