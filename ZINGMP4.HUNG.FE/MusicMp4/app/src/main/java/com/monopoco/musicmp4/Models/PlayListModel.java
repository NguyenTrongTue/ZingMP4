package com.monopoco.musicmp4.Models;

import com.monopoco.musicmp4.R;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class PlayListModel implements Serializable {

    private long id;

    private int Image;

    private String playListName;

    private List<SongModel> songModelList;



    public PlayListModel(int image, String playListName) {
        Image = image;
        this.playListName = playListName;
    }

    public PlayListModel(long id, int image, String playListName, List<SongModel> songModelList) {
        this.id = id;
        Image = image;
        this.playListName = playListName;
        this.songModelList = songModelList;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<SongModel> getSongModelList() {
        return songModelList;
    }

    public void setSongModelList(List<SongModel> songModelList) {
        this.songModelList = songModelList;
    }
}
