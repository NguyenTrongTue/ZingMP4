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

    public static PlayListModel playListModel1 = new PlayListModel(
           1, R.drawable.playlist1, "Liked song", SongModel.songModelList1
    );

    public static PlayListModel playListModel2 = new PlayListModel(
           2, R.drawable.playlist2, "Hello World!", SongModel.songModelList2
    );

    public static PlayListModel playListModel3 = new PlayListModel(
          3,  R.drawable.playlist3, "Queen Song", SongModel.songModelList3
    );

    public static PlayListModel playListModel4 = new PlayListModel(
          4,  R.drawable.playlist4, "Motivation", SongModel.songModelList4
    );

    public static List<PlayListModel> playListModelList = Arrays.asList(
            playListModel1, playListModel2, playListModel3, playListModel4
    );

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
