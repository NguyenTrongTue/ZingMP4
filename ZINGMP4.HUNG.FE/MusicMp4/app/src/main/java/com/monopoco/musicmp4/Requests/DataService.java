package com.monopoco.musicmp4.Requests;

import com.monopoco.musicmp4.Models.PlayListAddSongModel;
import com.monopoco.musicmp4.Models.PlayListModel;
import com.monopoco.musicmp4.Models.SearchModel;
import com.monopoco.musicmp4.Models.SongModel;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DataService {

    @GET("Song/get_trending")
    Call<List<SongModel>> GetPlaylistCurrentDay();

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("Song/search_song")
    Call<List<SongModel>> SearchSong(@Body SearchModel searchModel);

    @GET("Playlist/get_playlist_by_user")
    Call<List<PlayListModel>> GetPlayListOfUser(@Query("user_id") String userId);

    @GET("Playlist/get_playlist")
    Call<PlayListModel> GetPlayListById(@Query("playlist_id") String playlistId);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("Playlist/add_song_playlist")
    Call<Integer> addSongToPlayList(@Body PlayListAddSongModel body);


    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("Playlist/add_song_playlist")
    Call<Integer> AddNewPlaylist(@Body PlayListAddSongModel body);

}
