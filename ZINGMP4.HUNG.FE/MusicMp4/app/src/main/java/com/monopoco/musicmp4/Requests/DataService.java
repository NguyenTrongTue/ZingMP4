package com.monopoco.musicmp4.Requests;

import com.monopoco.musicmp4.Models.NewPlaylistModel;
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
    Call<PlayListModel> addSongToPlayList(@Body PlayListAddSongModel body);


    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("Playlist/add_playlist")
    Call<PlayListModel> AddNewPlaylist(@Body NewPlaylistModel body);

    @GET("Song/get_song_by_random")
    Call<SongModel> GetRandomSong();

    @GET("Song/get_song_liked_by_user")
    Call<List<SongModel>> GetLikedSongByUser(@Query("user_id") String userId);

    @GET("Song/is_liked_song")
    Call<Boolean> CheckLikedSong(@Query("user_id") String userId, @Query("song_id") String songId);

    @GET("Song/like_song")
    Call<Object> LikedSong(@Query("user_id") String userId, @Query("song_id") String songId);

}
