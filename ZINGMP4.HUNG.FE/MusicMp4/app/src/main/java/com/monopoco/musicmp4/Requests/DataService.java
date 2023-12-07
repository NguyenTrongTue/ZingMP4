package com.monopoco.musicmp4.Requests;

import com.monopoco.musicmp4.Models.SongModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {

    @GET("Song/get_trending")
    Call<List<SongModel>> GetPlaylistCurrentDay();

}
