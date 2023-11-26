package com.monopoco.musicmp4.Models;

public class PlayListModel {
    private int Image;

    private String playListName;

    public PlayListModel(int image, String playListName) {
        Image = image;
        this.playListName = playListName;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getPlayListName() {
        return playListName;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
    }
}
