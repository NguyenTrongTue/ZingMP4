package com.monopoco.musicmp4.Models;


import java.io.Serializable;

public class SongModel implements Serializable {

    private int Image;
    private String songName;
    private String singer;
    private int resource;

    public SongModel(int image, String songName, String singer, int resource) {
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
}
