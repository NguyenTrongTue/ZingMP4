package com.monopoco.musicmp4.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.monopoco.musicmp4.Adapters.SearchSongAdapter;
import com.monopoco.musicmp4.CallBack.ApiCallback;
import com.monopoco.musicmp4.Classes.ProfileEditBottomSheet;
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.Models.UserModel;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Requests.APIService;
import com.monopoco.musicmp4.Utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private Button editBtn;

    private TextView usernameView;

    private ImageView usernameImageView;

    private List<SongModel> recentlyPlayed = new ArrayList<>();

    private UserModel userModel;

    private SwipeRefreshLayout refreshLayout;

    private RecyclerView songRecently;

    private SearchSongAdapter songAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        getUserInfo(new ApiCallback<UserModel>() {
            @Override
            public void onApiSuccess(UserModel S) {
                userModel = S;
                initValue(view);
            }

            @Override
            public void onApiFailure(Throwable t) {
                Toast.makeText(getContext(), "Server error", Toast.LENGTH_LONG).show();
            }
        });
        // Inflate the layout for this fragment

        return view;
    }

    private void initValue(View view) {
        if (userModel != null) {
            editBtn = view.findViewById(R.id.btn_edit_profile);

            usernameView = view.findViewById(R.id.profile_username);

            usernameImageView = view.findViewById(R.id.profile_user_image);

            usernameView.setText(userModel.getUsername());

            refreshLayout = view.findViewById(R.id.swipe_refresh);
            songRecently = view.findViewById(R.id.rcl_song_recently);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            songRecently.setLayoutManager(linearLayoutManager);

            getDataRecentlyPlayed(new ApiCallback<List<SongModel>>() {
                @Override
                public void onApiSuccess(List<SongModel> S) {
                    recentlyPlayed = S;
                    updateRecycleView();
                }

                @Override
                public void onApiFailure(Throwable t) {

                }
            });


            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getDataRecentlyPlayed(new ApiCallback<List<SongModel>>() {
                        @Override
                        public void onApiSuccess(List<SongModel> S) {
                            recentlyPlayed = S;
                            updateRecycleView();
                            refreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onApiFailure(Throwable t) {
                            refreshLayout.setRefreshing(false);
                        }
                    });
                }
            });

            if (userModel.getAvatar() != null && !userModel.getAvatar().isEmpty()) {
                ImageUtils.setImageUrl(userModel.getAvatar(), usernameImageView, getContext());
            } else {
                usernameImageView.setImageResource(R.drawable.defaul_image_profile);
            }

            getDataRecentlyPlayed(new ApiCallback<List<SongModel>>() {
                @Override
                public void onApiSuccess(List<SongModel> S) {

                }

                @Override
                public void onApiFailure(Throwable t) {

                }
            });
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProfileEditBottomSheet dialog = new ProfileEditBottomSheet(userModel, ProfileFragment.this);
                    dialog.show(getActivity().getSupportFragmentManager(), "profile");
                }
            });

        }
    }

    public void updateUser(UserModel newUserModel) {
        getUserInfo(new ApiCallback<UserModel>() {
            @Override
            public void onApiSuccess(UserModel S) {
                userModel = S;
                usernameView.setText(userModel.getUsername());
                if (userModel.getAvatar() != null && !userModel.getAvatar().isEmpty()) {
                    ImageUtils.setImageUrl(userModel.getAvatar(), usernameImageView, getContext());
                } else {
                    usernameImageView.setImageResource(R.drawable.defaul_image_profile);
                }
            }

            @Override
            public void onApiFailure(Throwable t) {
//                Toast.makeText(getContext(), "Server error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateRecycleView() {
        songAdapter = new SearchSongAdapter(recentlyPlayed, getContext());
        songRecently.setAdapter(songAdapter);
    }

    private void getUserInfo(ApiCallback<UserModel> callback) {
        SharedPreferences sp = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String userId = sp.getString("userId", null);
        if (userId != null) {
            APIService.getService().GetUserById(userId).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if (response.code() == 200 && response.body() instanceof UserModel) {
                        callback.onApiSuccess(response.body());
                    } else {
                        Toast.makeText(getContext(), "Something wrong.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    callback.onApiFailure(t);
                }
            });
        }
    }

    private void getDataRecentlyPlayed(ApiCallback<List<SongModel>> callback) {
        if (userModel != null) {
            APIService.getService().GetRecentlyPlayed(userModel.getUserId()).enqueue(new Callback<List<SongModel>>() {
                @Override
                public void onResponse(Call<List<SongModel>> call, Response<List<SongModel>> response) {
                    if (response.code() == 200) {
                        callback.onApiSuccess(response.body());
                    } else {
                        Toast.makeText(getContext(), "Something wrong", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<SongModel>> call, Throwable t) {
                    callback.onApiFailure(t);
                }
            });
        }
    }
}