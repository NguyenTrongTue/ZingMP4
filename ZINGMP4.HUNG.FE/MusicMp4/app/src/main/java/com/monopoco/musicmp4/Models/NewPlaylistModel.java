package com.monopoco.musicmp4.Models;

import com.google.gson.annotations.SerializedName;

public class NewPlaylistModel {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("playlist_name")
    private String playlistName;

    @SerializedName("playback_mode")
    private Integer playBackMode = 1;

    @SerializedName("is_public")
    private Boolean isPublic = true;

    public NewPlaylistModel(String userId, String playlistName, Integer playBackMode, Boolean isPublic) {
        this.userId = userId;
        this.playlistName = playlistName;
        this.playBackMode = playBackMode;
        this.isPublic = isPublic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public Integer getPlayBackMode() {
        return playBackMode;
    }

    public void setPlayBackMode(Integer playBackMode) {
        this.playBackMode = playBackMode;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }
}
