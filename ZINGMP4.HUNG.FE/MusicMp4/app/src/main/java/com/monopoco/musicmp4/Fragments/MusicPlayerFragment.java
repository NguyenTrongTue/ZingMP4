package com.monopoco.musicmp4.Fragments;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.monopoco.musicmp4.Classes.ApplicationClass.ACTION_NEXT;
import static com.monopoco.musicmp4.Classes.ApplicationClass.ACTION_PLAY;
import static com.monopoco.musicmp4.Classes.ApplicationClass.ACTION_PREVIOUS;
import static com.monopoco.musicmp4.Classes.ApplicationClass.CHANNEL_ID_2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.support.v4.media.session.MediaSessionCompat;
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
import com.monopoco.musicmp4.Receiver.NotificationReceiver;
import com.monopoco.musicmp4.Utils.ImageUtils;

import java.io.ByteArrayOutputStream;

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

    private MediaSessionCompat mediaSessionCompat;

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
        mediaSessionCompat = new MediaSessionCompat(getActivity().getBaseContext(), "My Audio");
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        songImageView = view.findViewById(R.id.song_image_player);
        songName = view.findViewById(R.id.song_name);
        singer = view.findViewById(R.id.singer_name);
        shuffleButton = view.findViewById(R.id.music_shuffle);
        skipBackButton = view.findViewById(R.id.music_skip_back);
        playOrPauseButton = view.findViewById(R.id.music_play_pause);
        skipFwdButton = view.findViewById(R.id.music_skip_fwd);
        repeatButton = view.findViewById(R.id.music_repeat);
        mSeekbar = view.findViewById(R.id.music_seekbar);
        mTimeFrom = view.findViewById(R.id.music_time_from);
        mTimeEnd = view.findViewById(R.id.music_time_end);
        initValue(view);
        skipFwdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PlayerActivity)getActivity()).onSkipFwd();
            }
        });

        skipBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PlayerActivity)getActivity()).onSkipBack();
            }
        });

        playOrPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PlayerActivity)getActivity()).playOrPauseClick();
            }
        });


        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PlayerActivity)getActivity()).onRepeatClick(repeatButton);
            }
        });

        if (isPlaying) {
            playOrPauseButton.setImageResource(R.drawable.ic_pause);
            ((PlayerActivity)getActivity()).showNotification(R.drawable.ic_pause);
        } else {
            playOrPauseButton.setImageResource(R.drawable.ic_play);
            ((PlayerActivity)getActivity()).showNotification(R.drawable.ic_play);
        }
    }

    public ImageView getPlayOrPauseButton() {
        return playOrPauseButton;
    }

    public void setPlayOrPauseButton(ImageView playOrPauseButton) {
        this.playOrPauseButton = playOrPauseButton;
    }

    public ImageView getShuffleButton() {
        return shuffleButton;
    }

    public void setShuffleButton(ImageView shuffleButton) {
        this.shuffleButton = shuffleButton;
    }

    public ImageView getRepeatButton() {
        return repeatButton;
    }

    public void setRepeatButton(ImageView repeatButton) {
        this.repeatButton = repeatButton;
    }

    public void initValue(View view) {
        if (view != null) {
//            songImageView.setImageResource(songModel.getImage());
            ImageUtils.setImageUrl(songModel.getImageUrl(), songImageView, getContext());
            Animation rotation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
            rotation.setRepeatCount(Animation.INFINITE);
            seekBarHandler = new Handler();
            songImageView.setAnimation(rotation);

            songName.setSelected(true);
            songName.setText(songModel.getSongName());
            singer.setSelected(true);
            singer.setText(songModel.getSinger());

            playOrPauseButton.setImageResource(R.drawable.ic_pause);

            MediaPlayer mediaPlayer = ((PlayerActivity) getActivity()).getMediaPlayerService().getMediaPlayer();
            int duration = mediaPlayer.getDuration();
            int minute = (duration / 1000)  / 60;
            int second = (int)((duration / 1000) % 60);
            mSeekbar.setMax(duration / 1000);
            mTimeEnd.setText(String.format("%d:%02d", minute, second ));

            // handle change seekbar position
            handler = new Handler();
            mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress * 1000);
                        int duration = mediaPlayer.getCurrentPosition();
                        int minute = (duration / 1000)  / 60;
                        int second = (int)((duration / 1000) % 60);
                        mTimeFrom.setText(String.format("%d:%02d", minute, second ));
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
        }
    }
}