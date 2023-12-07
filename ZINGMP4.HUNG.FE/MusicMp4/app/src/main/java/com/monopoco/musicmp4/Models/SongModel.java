package com.monopoco.musicmp4.Models;


import com.google.gson.annotations.SerializedName;
import com.monopoco.musicmp4.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SongModel implements Serializable {

    @SerializedName("song_id")
    private String id;

    @SerializedName("thumnail")
    private String imageUrl;

    @SerializedName("song_name")
    private String songName;

    @SerializedName("singer_name")
    private String singer;


    @SerializedName("link_song")
    private String resource;

    public SongModel(String id, String imageUrl, String songName, String singer, String resource) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.songName = songName;
        this.singer = singer;
        this.resource = resource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
