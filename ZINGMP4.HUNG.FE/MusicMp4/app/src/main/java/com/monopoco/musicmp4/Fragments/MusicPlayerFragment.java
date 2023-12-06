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
                onSkipFwd();
            }
        });

        skipBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSkipBack();
            }
        });

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
        if (isPlaying) {
            playOrPauseButton.setImageResource(R.drawable.ic_pause);
            showNotification(R.drawable.ic_pause);
        } else {
            playOrPauseButton.setImageResource(R.drawable.ic_play);
            showNotification(R.drawable.ic_play);
        }



    }


    private void onSkipFwd() {
        ((PlayerActivity) getActivity()).getMediaPlayerService().nextSong();
        songModel = ((PlayerActivity) getActivity()).getMediaPlayerService().getCurrentSong();
        initValue(this.getView());
        showNotification(R.drawable.ic_pause);
    }

    private void onSkipBack() {
        ((PlayerActivity) getActivity()).getMediaPlayerService().preSong();
        songModel = ((PlayerActivity) getActivity()).getMediaPlayerService().getCurrentSong();
        initValue(this.getView());
        showNotification(R.drawable.ic_pause);
    }

    private void onPauseClick() {
        ((PlayerActivity) getActivity()).getMediaPlayerService().getMediaPlayer().pause();
        playOrPauseButton.setImageResource(R.drawable.ic_play);
        showNotification(R.drawable.ic_play);
    }

    private void onPlayClick() {
        ((PlayerActivity) getActivity()).getMediaPlayerService().getMediaPlayer().start();
        playOrPauseButton.setImageResource(R.drawable.ic_pause);
        showNotification(R.drawable.ic_pause);
    }

    public void initValue(View view) {
        if (view != null) {
            songImageView.setImageResource(songModel.getImage());
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
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    onPauseClick();
                }
            });
        }
    }

    public void showNotification(int playOrPauseBtn) {
        Intent intent = new Intent(getContext(), PlayerActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Intent prevIntent = new Intent(getContext(), NotificationReceiver.class)
                .setAction(ACTION_PREVIOUS);
        PendingIntent prevPending = PendingIntent.getBroadcast(getContext(), 0, prevIntent, PendingIntent.FLAG_IMMUTABLE);

        Intent pauseIntent = new Intent(getContext(), NotificationReceiver.class)
                .setAction(ACTION_PLAY);
        PendingIntent pausePending = PendingIntent.getBroadcast(getContext(), 0, pauseIntent, PendingIntent.FLAG_IMMUTABLE);

        Intent nextIntent = new Intent(getContext(), NotificationReceiver.class)
                .setAction(ACTION_PREVIOUS);
        PendingIntent nextPending = PendingIntent.getBroadcast(getContext(), 0, nextIntent, PendingIntent.FLAG_IMMUTABLE);

        byte[] picture = null;

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), songModel.getImage());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        picture = stream.toByteArray();

        Bitmap thumb = null;
        if (picture != null) {
            thumb = BitmapFactory.decodeByteArray(picture, 0, picture.length);
        } else {
            thumb = BitmapFactory.decodeResource(getResources(), R.drawable.queen1);
        }

        Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_ID_2)
                .setSmallIcon(R.drawable.ic_play)
                .setLargeIcon(thumb)
                .setContentTitle(songModel.getSongName())
                .setContentText(songModel.getSinger())
                .addAction(R.drawable.ic_skip_back, "Previous", prevPending)
                .addAction(playOrPauseBtn, "Pause", pausePending)
                .addAction(R.drawable.ic_skip_fwd, "Next", nextPending)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSessionCompat.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOnlyAlertOnce(true)
                .build();

        NotificationManager notificationManager =(NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }


}