package com.monopoco.musicmp4.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.monopoco.musicmp4.Activities.PlayListActivity;
import com.monopoco.musicmp4.Activities.PlayerActivity;
import com.monopoco.musicmp4.Adapters.GridAdapter;
import com.monopoco.musicmp4.Adapters.LibraryAdapter;
import com.monopoco.musicmp4.Models.NewPlaylistModel;
import com.monopoco.musicmp4.Models.PlayListModel;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Requests.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;

    private GridView gridView;

    private ImageView btnAddNew;

    private LibraryAdapter libraryAdapter;

    private List<PlayListModel> playListModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

        btnAddNew = view.findViewById(R.id.lbr_btn_add);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddNew.setClickable(false);
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.new_playlist_bottom);

                EditText editText = dialog.findViewById(R.id.pls_name_field);
                AppCompatButton submitBtn = dialog.findViewById(R.id.pls_btn_add);

                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addNewPlaylist(editText.getText().toString());
                        dialog.dismiss();
                        btnAddNew.setClickable(true);
                        getPlayListModels();
                    }
                });

                ImageView closeBtn = dialog.findViewById(R.id.new_pl_close);


                editText.requestFocus();
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        btnAddNew.setClickable(true);
                    }
                });

                dialog.show();
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPlayListModels();

            }
        });

        gridView = view.findViewById(R.id.library_grid_view);
        getPlayListModels();

        return view;
    }

    private void getPlayListModels() {

        List<PlayListModel> playListModels = new ArrayList<>();

        String userID = "4e0907f7-c69f-47eb-9bad-140357181195";
        APIService.getService().GetPlayListOfUser(userID).enqueue(new Callback<List<PlayListModel>>() {
            @Override
            public void onResponse(Call<List<PlayListModel>> call, Response<List<PlayListModel>> response) {
                if (response.body() != null) {
                    playListModels.addAll(response.body());
                    playListModelList = playListModels;

                    libraryAdapter = new LibraryAdapter(playListModelList, getContext());
                    gridView.setAdapter(libraryAdapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity(), PlayListActivity.class);
                            intent.putExtra("playlistId", playListModelList.get(position).getPlaylistId());
                            startActivity(intent);
                        }
                    });
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PlayListModel>> call, Throwable t) {
                Log.e("monopoco", "Call get playlist Error");
                Toast.makeText(getContext(), "Có lỗi xảy ra", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void addNewPlaylist(String playlistName) {
        NewPlaylistModel newPlaylistModel = new NewPlaylistModel(
                "4e0907f7-c69f-47eb-9bad-140357181195",
                playlistName,
                1,
                true
        );
        APIService.getService().AddNewPlaylist(newPlaylistModel).enqueue(new Callback<PlayListModel>() {
            @Override
            public void onResponse(Call<PlayListModel> call, Response<PlayListModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null && response.body().getPlaylistId() != null) {
                        Intent intent = new Intent(getActivity(), PlayListActivity.class);
                        intent.putExtra("playlistId", response.body().getPlaylistId());
                        startActivity(intent);
                        Toast.makeText(getContext(), "New playlist successfully added", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<PlayListModel> call, Throwable t) {
                Toast.makeText(getContext(), "Có lỗi xảy ra", Toast.LENGTH_LONG).show();
            }
        });
    }


}