package com.monopoco.musicmp4.Requests;

import com.monopoco.musicmp4.Models.LoginModel;
import com.monopoco.musicmp4.Models.NewPlaylistModel;
import com.monopoco.musicmp4.Models.PlayListAddSongModel;
import com.monopoco.musicmp4.Models.PlayListModel;
import com.monopoco.musicmp4.Models.RegisterModel;
import com.monopoco.musicmp4.Models.SearchModel;
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.Models.UserModel;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @POST("Playlist/add_song_playlist")
    Observable<PlayListModel> addSongToPlayListObser(@Body PlayListAddSongModel body);


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

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("User/login")
    Call<UserModel> Login(@Body LoginModel body);

//    @Headers({"Accept: application/json", "Content-Type: multipart/form-data"})
    @Multipart
    @POST("User/register")
    Call<UserModel> Register(@Part("username") RequestBody username, @Part("password") RequestBody password, @Part("email") RequestBody email);

    @GET("User/get_new_password")
    Call<Object> GetNewPassword(@Query("email") String email);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("Song/update_listen_of_number")
    Call<Object> UpdateListenOfNumber(@Query("song_id") String songId, @Query("user_id") String userId);

    @GET("User/get_recently_played")
    Call<List<SongModel>> GetRecentlyPlayed(@Query("user_id") String userId);

    @GET("User/get_user_by_id")
    Call<UserModel> GetUserById(@Query("id") String userId);

    @Multipart
//    @Headers({"Accept: application/json", "Content-Type: multipart/form-data"})
    @POST("User/edit_user_info")
    Call<UserModel> EditUser(@Part("user_name") RequestBody username, @Part("email") RequestBody email, @Part MultipartBody.Part avatar);

    @GET("Playlist/check_song_exists_in_playlist")
    Call<Boolean> CheckSongInPlaylist(@Query("playlist_id") String playlistId, @Query("song_id") String songId);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("Playlist/delete_song_playlist")
    Call<Integer> DeleteSongFromPlayList(@Body PlayListAddSongModel body);
}
