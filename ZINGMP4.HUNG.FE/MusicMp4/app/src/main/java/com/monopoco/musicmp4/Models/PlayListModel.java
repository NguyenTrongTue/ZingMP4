package com.monopoco.musicmp4.Models;

import com.google.gson.annotations.SerializedName;
import com.monopoco.musicmp4.R;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class PlayListModel implements Serializable {

    @SerializedName("playlist_id")
    private String playlistId;

//    private String  Image;

    @SerializedName("playlist_name")
    private String playListName;

    public PlayListModel(String playlistId, String playListName, List<SongModel> songModelList) {
        this.playlistId = playlistId;
        this.playListName = playListName;
        this.songModelList = songModelList;
    }

    @SerializedName("song_entities")
    private List<SongModel> songModelList;

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlayListName() {
        return playListName;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
    }

    public List<SongModel> getSongModelList() {
        return songModelList;
    }

    public void setSongModelList(List<SongModel> songModelList) {
        this.songModelList = songModelList;
    }
}
