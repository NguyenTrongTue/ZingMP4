package com.monopoco.musicmp4.Fragments;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.R;

public class MusicPlayerFragment extends Fragment {

    private ImageView songImageView;

    private SongModel songModel;

    private TextView songName;

    private TextView singer;

    public MusicPlayerFragment() {
        // Required empty public constructor
    }

    public MusicPlayerFragment(SongModel songModel) {
        this.songModel = songModel;
    }

    public SongModel getSongModel() {
        return songModel;
    }

    public void setSongModel(SongModel songModel) {
        this.songModel = songModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_player, container, false);
        songImageView = view.findViewById(R.id.song_image_player);
        songImageView.setImageResource(songModel.getImage());
        Animation rotation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        rotation.setRepeatCount(Animation.INFINITE);
        songImageView.setAnimation(rotation);

        songName = view.findViewById(R.id.song_name);
        songName.setSelected(true);
        songName.setText(songModel.getSongName());

        singer = view.findViewById(R.id.singer_name);
        singer.setSelected(true);
        singer.setText(songModel.getSinger());
        // Inflate the layout for this fragment
        return view;
    }
}