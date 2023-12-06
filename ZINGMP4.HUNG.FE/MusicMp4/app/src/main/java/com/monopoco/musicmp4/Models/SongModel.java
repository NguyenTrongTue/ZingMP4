package com.monopoco.musicmp4.Models;


import com.monopoco.musicmp4.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SongModel implements Serializable {

    private long id;
    private int Image;
    private String songName;
    private String singer;
    private int resource;

    private int numberOfListens;
    public static SongModel song1 = new SongModel(1, R.drawable.queen1, "Bohemian Rhapsody", "Queen",R.raw.silent_night);
    public static SongModel song2 = new SongModel(2, R.drawable.queen2, "Crazy Little Thing Called Love", "Queen", R.raw.our_last_chrismas);
    public static SongModel song3 = new SongModel(3, R.drawable.queen3, "I Was Born To Love You", "Queen", R.raw.silent_night);
    public static SongModel song4 = new SongModel(4, R.drawable.queen4, "Somebody To Love", "Queen", R.raw.silent_night);

    public static SongModel song5 = new SongModel(5, R.drawable.queen1, "Bohemian Rhapsody", "Queen",R.raw.our_last_chrismas);
    public static SongModel song6 = new SongModel(6,R.drawable.queen2, "Crazy Little Thing Called Love", "Queen", R.raw.our_last_chrismas);
    public static SongModel song7 = new SongModel(7,R.drawable.queen3, "I Was Born To Love You", "Queen", R.raw.silent_night);
    public static SongModel song8 = new SongModel(8, R.drawable.queen4, "Somebody To Love", "Queen", R.raw.silent_night);

    public static SongModel song9 = new SongModel(9,R.drawable.queen1, "Bohemian Rhapsody", "Queen",R.raw.silent_night);
    public static SongModel song10 = new SongModel(10, R.drawable.queen2, "Crazy Little Thing Called Love", "Queen", R.raw.our_last_chrismas);
    public static SongModel song11 = new SongModel(11, R.drawable.queen3, "I Was Born To Love You", "Queen", R.raw.silent_night);
    public static SongModel song12 = new SongModel(12, R.drawable.queen4, "Somebody To Love", "Queen", R.raw.silent_night);

    public static List<SongModel> songModelList1 = new ArrayList<>(Arrays.asList(song1, song2, song3, song4, song5, song6));

    public static List<SongModel> allSongFake = new ArrayList<>(Arrays.asList(song1, song2, song3, song4, song5, song6, song7, song8, song9, song10, song11, song12));

    public static List<SongModel> songModelList2 = new ArrayList<>(Arrays.asList(song1, song3, song5, song7, song9));

    public static List<SongModel> songModelList3 = new ArrayList<>(Arrays.asList(song1, song2, song3, song4, song5, song6, song10));

    public static List<SongModel> songModelList4 = new ArrayList<>(Arrays.asList(song1, song2, song3, song4, song5, song10, song11, song12));

    public SongModel(long id, int image, String songName, String singer, int resource) {
        this.id = id;
        Image = image;
        this.songName = songName;
        this.singer = singer;
        this.resource = resource;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumberOfListens() {
        return numberOfListens;
    }

    public void setNumberOfListens(int numberOfListens) {
        this.numberOfListens = numberOfListens;
    }
}
