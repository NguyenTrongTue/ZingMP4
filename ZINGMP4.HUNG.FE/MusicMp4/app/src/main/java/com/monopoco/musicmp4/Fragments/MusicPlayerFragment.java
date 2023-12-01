package com.monopoco.musicmp4.Fragments;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.monopoco.musicmp4.Activities.PlayerActivity;
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Services.MediaPlayerService;

public class MusicPlayerFragment extends Fragment {

    private ImageView songImageView;

    private SongModel songModel;

    private TextView songName;

    private TextView singer;

    private ImageView shuffleButton;

    private ImageView skipBackButton;

    private ImageView playOrPauseButton;

    private ImageView skipFwdButton;

    private ImageView repeatButton;

    private boolean isPlaying = false;

    private SeekBar mSeekbar;

    private TextView mTimeFrom;

    private TextView mTimeEnd;

    private Handler seekBarHandler;

    private Handler handler;

    private Runnable runnable;


    public MusicPlayerFragment(SongModel songModel, boolean isPlaying) {
        this.songModel = songModel;
        this.isPlaying = isPlaying;
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

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        songImageView = view.findViewById(R.id.song_image_player);
        songImageView.setImageResource(songModel.getImage());
        Animation rotation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        rotation.setRepeatCount(Animation.INFINITE);
        seekBarHandler = new Handler();
        songImageView.setAnimation(rotation);

        songName = view.findViewById(R.id.song_name);
        songName.setSelected(true);
        songName.setText(songModel.getSongName());
        handler = new Handler();
        singer = view.findViewById(R.id.singer_name);
        singer.setSelected(true);
        singer.setText(songModel.getSinger());
        initValue(view);
        playOrPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (((PlayerActivity) getActivity()).getMediaPlayerService() != null && ((PlayerActivity) getActivity()).getMediaPlayerService().getMediaPlayer() != null) {
                        if (((PlayerActivity) getActivity()).getMediaPlayerService().getMediaPlayer().isPlaying()) {
                            onPauseClick();
                        } else {
                            onPlayClick();
                        }
                    }
                }
            }
        });
    }


    private void onPauseClick() {
        ((PlayerActivity) getActivity()).getMediaPlayerService().getMediaPlayer().pause();
        playOrPauseButton.setImageResource(R.drawable.ic_play);
    }

    private void onPlayClick() {
        ((PlayerActivity) getActivity()).getMediaPlayerService().getMediaPlayer().start();
//        if (mSeekbar.getProgress() > 0) {
//            ((PlayerActivity) getActivity()).getMediaPlayerService().getMediaPlayer().seekTo(mSeekbar.getProgress() * 1000);
//        } else {
//        }
        playOrPauseButton.setImageResource(R.drawable.ic_pause);
    }

    public void initValue(View view) {
        if (view != null) {
            shuffleButton = view.findViewById(R.id.music_shuffle);
            skipBackButton = view.findViewById(R.id.music_skip_back);
            playOrPauseButton = view.findViewById(R.id.music_play_pause);
            skipFwdButton = view.findViewById(R.id.music_skip_fwd);
            repeatButton = view.findViewById(R.id.music_repeat);
            mSeekbar = view.findViewById(R.id.music_seekbar);
            mTimeFrom = view.findViewById(R.id.music_time_from);
            mTimeEnd = view.findViewById(R.id.music_time_end);;
            if (isPlaying) {
                playOrPauseButton.setImageResource(R.drawable.ic_pause);
            } else {
                playOrPauseButton.setImageResource(R.drawable.ic_play);
            }

            MediaPlayer mediaPlayer = ((PlayerActivity) getActivity()).getMediaPlayerService().getMediaPlayer();
            int duration = mediaPlayer.getDuration();
            int minute = (duration / 1000)  / 60;
            int second = (int)((duration / 1000) % 60);
            mSeekbar.setMax(duration / 1000);
            mTimeEnd.setText(String.format("%d:%02d", minute, second ));

            // handle change seekbar position
            mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress * 1000);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            // set progress of seek Bar with time
            runnable = new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null ) {
                        if ( mediaPlayer.isPlaying()) {
                            mSeekbar.setProgress(mediaPlayer.getCurrentPosition() / 1000);
                            int duration = mediaPlayer.getCurrentPosition();
                            int minute = (duration / 1000)  / 60;
                            int second = (int)((duration / 1000) % 60);
                            mTimeFrom.setText(String.format("%d:%02d", minute, second ));
                        }
                    } else {
                        mSeekbar.setProgress(0);
                    }
                    handler.postDelayed(runnable, 1000);

                }
            };

            handler.postDelayed(runnable, 1000);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    onPauseClick();
                }
            });
        }
    }

}