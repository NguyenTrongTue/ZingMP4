package com.monopoco.musicmp4.Models;

import com.google.gson.annotations.SerializedName;

public class PlayListAddSongModel {

    @SerializedName("playlist_id")
    private String playListId;

    @SerializedName("song_id")
    private String songId;

    public PlayListAddSongModel(String playListId, String songId) {
        this.playListId = playListId;
        this.songId = songId;
    }

    public String getPlayListId() {
        return playListId;
    }

    public void setPlayListId(String playListId) {
        this.playListId = playListId;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }
}
