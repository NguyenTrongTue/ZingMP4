package com.monopoco.musicmp4.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Utils.ImageUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class GridAdapter extends BaseAdapter {
    private List<SongModel> songModelList;
    private Context context;

    public GridAdapter(List<SongModel> songModelList, Context context) {
        this.songModelList = songModelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return songModelList != null ? songModelList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return songModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = LayoutInflater.from(context).inflate(R.layout.liked_song_item, parent,false);
        }
        ImageView imageView = convertView.findViewById(R.id.image_song);
        TextView songName = convertView.findViewById(R.id.song_name);
        TextView singerName = convertView.findViewById(R.id.singer_name);

        // Set value
        ImageUtils.setImageUrl(songModelList.get(position).getImageUrl(), imageView, convertView.getContext());
        songName.setText(songModelList.get(position).getSongName());
        singerName.setText(songModelList.get(position).getSinger());
        return convertView;
    }
}
