package com.monopoco.musicmp4.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

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

import com.monopoco.musicmp4.Activities.PlayListActivity;
import com.monopoco.musicmp4.Activities.PlayerActivity;
import com.monopoco.musicmp4.Adapters.GridAdapter;
import com.monopoco.musicmp4.Adapters.LibraryAdapter;
import com.monopoco.musicmp4.Models.PlayListModel;
import com.monopoco.musicmp4.R;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;

    private GridView gridView;

    private ImageView btnAddNew;

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

                ImageView closeBtn = dialog.findViewById(R.id.new_pl_close);

                EditText editText = dialog.findViewById(R.id.pls_name_field);

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
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        gridView = view.findViewById(R.id.library_grid_view);
        playListModelList = getPlayListModels();

        LibraryAdapter libraryAdapter = new LibraryAdapter(playListModelList, getContext());
        gridView.setAdapter(libraryAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PlayListActivity.class);
                intent.putExtra("playlistInfo", playListModelList.get(position));
                startActivity(intent);
            }
        });

        return view;
    }

    private List<PlayListModel> getPlayListModels() {
//        if (PlayListModel.playListModelList != null) {
//            return PlayListModel.playListModelList;
//        } else {
//        }
        return new ArrayList<>();
    }
}