package com.monopoco.musicmp4.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.monopoco.musicmp4.Activities.PlayListActivity;
import com.monopoco.musicmp4.Activities.PlayerActivity;
import com.monopoco.musicmp4.Interfaces.ItemClickListener;
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Utils.ImageUtils;

import java.util.List;

public class InsertSongAdapter extends RecyclerView.Adapter<InsertSongAdapter.ViewHolder> {

    private List<SongModel> songModelList;

    private Context context;

    public InsertSongAdapter(List<SongModel> songModelList, Context context) {
        this.songModelList = songModelList;
        this.context = context;
    }

    public List<SongModel> getSongModelList() {
        return songModelList;
    }

    public void setSongModelList(List<SongModel> songModelList) {
        this.songModelList = songModelList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.insert_song_item, parent, false);
        Log.e("Cjecskmksdld,sd", "fmsbjssd");
        return new InsertSongAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtSongName.setText(songModelList.get(position).getSongName());
        holder.txtSingerName.setText(songModelList.get(position).getSinger());
//        holder.songImage.setImageResource(songModelList.get(position).getImage());
        ImageUtils.setImageUrl(songModelList.get(position).getImageUrl(), holder.songImage, getContext());
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof PlayListActivity) {
                    ((PlayListActivity) context).dismissDialog();
                    ((PlayListActivity) context).addSongToPlaylist(songModelList.get(holder.getAdapterPosition()).getId());
                }
                Log.e("monopoco", "Hello mother fucker");
            }
        });
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return songModelList != null ? songModelList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView txtSongName, txtSingerName;

        public ImageView songImage;

        public ImageView addButton;

        public ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Ánh xạ
            txtSongName = itemView.findViewById(R.id.s_song_name);
            txtSingerName = itemView.findViewById(R.id.s_singer);
            songImage = itemView.findViewById(R.id.s_image);
            addButton = itemView.findViewById(R.id.insert_add);
            addButton.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v, getAdapterPosition());
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
}
