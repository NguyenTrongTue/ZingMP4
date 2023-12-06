package com.monopoco.musicmp4.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.monopoco.musicmp4.Activities.PlayerActivity;
import com.monopoco.musicmp4.Interfaces.ItemClickListener;
import com.monopoco.musicmp4.Models.PlayListModel;
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class SearchSongAdapter extends RecyclerView.Adapter<SearchSongAdapter.ViewHolder>{

    private List<SongModel> songModelList;

    private Context context;


    public SearchSongAdapter(List<SongModel> songModelList, Context context) {
        this.songModelList = songModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    public void showDialog(SongModel songModel) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_song_layout);
        TextView close = dialog.findViewById(R.id.bts_txt_close);
        BlurView blurView = dialog.findViewById(R.id.blur_layout);
        float radius = 3f;

        View decorView = ((Activity) context).getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(context))
                .setBlurRadius(radius)
                .setHasFixedTransformationMatrix(true);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Ánh xạ
        LinearLayout addPlayList = dialog.findViewById(R.id.bts_add_playlist);
        LinearLayout share = dialog.findViewById(R.id.bts_share);
        LinearLayout viewArtists = dialog.findViewById(R.id.bts_artists);

        ImageView songImage  = dialog.findViewById(R.id.bts_song_img);
        songImage.setImageResource(songModel.getImage());
        TextView songName = dialog.findViewById(R.id.bts_txt_song);
        songName.setText(songModel.getSongName());
        TextView singer = dialog.findViewById(R.id.bts_txt_singer);
        singer.setText(songModel.getSinger());

        addPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Add Playlist", Toast.LENGTH_SHORT);
                dialog.dismiss();
                showDialogPlaylist();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Share", Toast.LENGTH_SHORT);
                dialog.dismiss();
            }
        });

        viewArtists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "View Artists", Toast.LENGTH_SHORT);
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void showDialogPlaylist() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.playlist_dialog);

        ListView listView = dialog.findViewById(R.id.bts_playlist_view);

        TextView txtClose = dialog.findViewById(R.id.bts_playlist_txt_close);
        AppCompatButton btnClose = dialog.findViewById(R.id.bts_playlist_btn_close);

        AppCompatButton btnAdd = dialog.findViewById(R.id.bts_playlist_btn_new);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNewPlaylistDialog();
            }
        });

        PlayListViewBottomAdapter playListViewBottomAdapter = new PlayListViewBottomAdapter(
                PlayListModel.playListModelList, context
        );

        listView.setAdapter(playListViewBottomAdapter);

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void showAddNewPlaylistDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.new_playlist_bottom);

        ImageView closeBtn = dialog.findViewById(R.id.new_pl_close);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtSongName.setText(songModelList.get(position).getSongName());
        holder.txtSingerName.setText(songModelList.get(position).getSinger());
        holder.songImage.setImageResource(songModelList.get(position).getImage());
        holder.moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(songModelList.get(holder.getAdapterPosition()));
                Log.e("monopoco", "Hello mother fucker");
            }
        });
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(v.getContext(), PlayerActivity.class);
                intent.putExtra("songsInfo", new ArrayList<SongModel>(Arrays.asList(songModelList.get(position))));
                v.getContext().startActivity(intent);
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

        public CardView itemSearch;

        public ImageView moreButton;

        public ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Ánh xạ
            txtSongName = itemView.findViewById(R.id.s_song_name);
            txtSingerName = itemView.findViewById(R.id.s_singer);
            songImage = itemView.findViewById(R.id.s_image);
            itemSearch = itemView.findViewById(R.id.item_search);
            moreButton = itemView.findViewById(R.id.s_more);
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
