package com.monopoco.musicmp4.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monopoco.musicmp4.Adapters.RecommendedSliderAdapter;
import com.monopoco.musicmp4.Models.RecommendedModel;
import com.monopoco.musicmp4.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private List<RecommendedModel> recommendedModels = new ArrayList<>();

    private LinearLayout linearRecommended;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recommendedModels.add(new RecommendedModel(R.drawable.queen1, "Bohemian Rhapsody", "Queen"));
        recommendedModels.add(new RecommendedModel(R.drawable.queen2, "Crazy Little Thing Called Love", "Queen"));
        recommendedModels.add(new RecommendedModel(R.drawable.queen3, "I Was Born To Love You", "Queen"));
        recommendedModels.add(new RecommendedModel(R.drawable.queen4, "Somebody To Love", "Queen"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        linearRecommended = view.findViewById(R.id.linear_recommended);
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        recommendedModels.forEach(recommendedModel -> {
            View viewItem = inflater.inflate(R.layout.recomment_slide_item, null);
            ImageView slideImage = viewItem.findViewById(R.id.image_song);
            slideImage.setImageResource(recommendedModel.getImage());
            TextView songName = viewItem.findViewById(R.id.song_name);
            songName.setText(recommendedModel.getSongName());
            TextView singerName = viewItem.findViewById(R.id.singer_name);
            singerName.setText(recommendedModel.getSinger());
            if (recommendedModels.indexOf(recommendedModel) == 0) {
                LinearLayout ll = new LinearLayout(getContext());
                ll.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(70, 0, 0, 0);
                linearRecommended.addView(viewItem, layoutParams);
            } else if (recommendedModels.indexOf(recommendedModel) == recommendedModels.size() - 1) {
                LinearLayout ll = new LinearLayout(getContext());
                ll.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 70, 0);
                linearRecommended.addView(viewItem, layoutParams);
            } else {
                linearRecommended.addView(viewItem);
            }
        });
        return view;
    }
}