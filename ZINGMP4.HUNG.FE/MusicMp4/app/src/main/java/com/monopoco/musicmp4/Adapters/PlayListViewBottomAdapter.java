package com.monopoco.musicmp4.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.monopoco.musicmp4.Models.PlayListModel;
import com.monopoco.musicmp4.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayListViewBottomAdapter extends BaseAdapter {

    private List<PlayListModel> playListModelList;

    private Context context;

    private Set<String> idsChosen;


    public PlayListViewBottomAdapter(List<PlayListModel> playListModelList, Context context) {
        this.playListModelList = playListModelList;
        this.context = context;
        idsChosen = new HashSet<>();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.playlist_choose_item, parent,false);
        }

        ImageView imageView = convertView.findViewById(R.id.playlist_item_image);
        TextView playlistName = convertView.findViewById(R.id.playlist_item_name);
        TextView playListSongs = convertView.findViewById(R.id.playlist_item_songs);

        ImageView checkRadio = convertView.findViewById(R.id.playlist_item_check);


//        imageView.setImageResource(playListModelList.get(position).getImage());
        playlistName.setText(playListModelList.get(position).getPlayListName());
        String songNumber;
        if (playListModelList.get(position).getSongModelList().size() == 0) {
            songNumber = "Empty";
        } else if (playListModelList.get(position).getSongModelList().size() == 1) {
            songNumber = "1 song";
        } else {
            songNumber = String.format("%d songs", playListModelList.get(position).getSongModelList().size());
        }
        playListSongs.setText(songNumber);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("monopco", String.valueOf(playListModelList.get(position)));

                Log.e("monopco", idsChosen.toString());

                if (idsChosen.contains(playListModelList.get(position).getPlaylistId())) {
                    idsChosen.remove(playListModelList.get(position).getPlaylistId());
                    checkRadio.setImageResource(R.drawable.ic_empty_circle);
                } else {
                    idsChosen.add(playListModelList.get(position).getPlaylistId());
                    checkRadio.setImageResource(R.drawable.ic_check_circle);
                }
            }
        });
        return convertView;
    }


}
