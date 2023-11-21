package com.monopoco.musicmp4.Models;


public class SongModel {

    private int Image;
    private String songName;
    private String singer;

    public SongModel(int image, String songName, String singer) {
        Image = image;
        this.songName = songName;
        this.singer = singer;
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
}
