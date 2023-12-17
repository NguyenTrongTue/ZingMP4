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
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Requests.APIService;
import com.monopoco.musicmp4.Utils.ImageUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayListViewBottomAdapter extends BaseAdapter {

    private List<PlayListModel> playListModelList;

    private Context context;

    private Set<String> idsChosen;

    private SongModel songModel;


    public PlayListViewBottomAdapter(List<PlayListModel> playListModelList, Context context, SongModel songModel) {
        this.playListModelList = playListModelList;
        this.context = context;
        idsChosen = new HashSet<>();
        this.songModel = songModel;
    }

    public List<PlayListModel> getPlayListModelList() {
        return playListModelList;
    }

    public void setPlayListModelList(List<PlayListModel> playListModelList) {
        this.playListModelList = playListModelList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Set<String> getIdsChosen() {
        return idsChosen;
    }

    public void setIdsChosen(Set<String> idsChosen) {
        this.idsChosen = idsChosen;
    }

    public SongModel getSongModel() {
        return songModel;
    }

    public void setSongModel(SongModel songModel) {
        this.songModel = songModel;
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

        if (playListModelList.get(position).getPlaylistImage() != null && !playListModelList.get(position).getPlaylistImage().isEmpty()) {
            ImageUtils.setImageUrl(playListModelList.get(position).getPlaylistImage(), imageView, parent.getContext());
        } else {
            imageView.setImageResource(R.drawable.playlist_empty);
        }
//        imageView.setImageResource(playListModelList.get(position).getImage());
        playlistName.setText(playListModelList.get(position).getPlayListName());
        String songNumber;
//        if (playListModelList.get(position).getSongModelList().size() == 0) {
//            songNumber = "Empty";
//        } else if (playListModelList.get(position).getSongModelList().size() == 1) {
//            songNumber = "1 song";
//        } else {
//            songNumber = String.format("%d songs", playListModelList.get(position).getSongModelList().size());
//        }
//        playListSongs.setText(songNumber);

        APIService.getService().CheckSongInPlaylist(playListModelList.get(position).getPlaylistId(), songModel.getId()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200) {
                    if (response.body()) {
                        idsChosen.add(playListModelList.get(position).getPlaylistId());
                        checkRadio.setImageResource(R.drawable.ic_check_circle);
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

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
