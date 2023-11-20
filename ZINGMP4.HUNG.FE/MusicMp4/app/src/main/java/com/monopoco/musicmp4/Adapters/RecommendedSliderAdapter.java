package com.monopoco.musicmp4.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.monopoco.musicmp4.Models.RecommendedModel;
import com.monopoco.musicmp4.R;

import java.util.List;

public class RecommendedSliderAdapter extends PagerAdapter {

    private Context context;
    private List<RecommendedModel> recommendedModelList;

    public RecommendedSliderAdapter(Context context, List<RecommendedModel> recommendedModelList) {
        this.context = context;
        this.recommendedModelList = recommendedModelList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.recomment_slide_item, null);
        ImageView slideImage = view.findViewById(R.id.image_song);
        slideImage.setImageResource(recommendedModelList.get(position).getImage());
        TextView songName = view.findViewById(R.id.song_name);
        songName.setText(recommendedModelList.get(position).getSongName());
        TextView singerName = view.findViewById(R.id.singer_name);
        singerName.setText(recommendedModelList.get(position).getSinger());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return this.recommendedModelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
