package com.monopoco.musicmp4.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.monopoco.musicmp4.Models.PlayListModel;
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.R;

import java.util.List;

public class LibraryAdapter extends BaseAdapter {
    private List<PlayListModel> playListModelList;
    private Context context;

    public LibraryAdapter(List<PlayListModel> playListModelList, Context context) {
        this.playListModelList = playListModelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return playListModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return playListModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = LayoutInflater.from(context).inflate(R.layout.library_item_adapter, parent,false);
        }
        ImageView imageView = convertView.findViewById(R.id.image_library);
        TextView libraryNameTxt = convertView.findViewById(R.id.library_name);
        TextView authTxt = convertView.findViewById(R.id.auth);

        // Set value
        imageView.setImageResource(playListModelList.get(position).getImage());
        libraryNameTxt.setText(playListModelList.get(position).getPlayListName());
        authTxt.setText(playListModelList.get(position).getPlayListName());
        return convertView;
    }

}
