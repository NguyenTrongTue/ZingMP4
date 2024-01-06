package com.monopoco.musicmp4.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.monopoco.musicmp4.Activities.MainActivity;
import com.monopoco.musicmp4.Activities.PlayListActivity;
import com.monopoco.musicmp4.Activities.PlayerActivity;
import com.monopoco.musicmp4.CallBack.ApiCallback;
import com.monopoco.musicmp4.Interfaces.ItemClickListener;
import com.monopoco.musicmp4.Models.PlayListAddSongModel;
import com.monopoco.musicmp4.Models.PlayListModel;
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Requests.APIService;
import com.monopoco.musicmp4.Utils.ImageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchSongAdapter extends RecyclerView.Adapter<SearchSongAdapter.ViewHolder>{

    private List<SongModel> songModelList;

    private Context context;

    private PlayListViewBottomAdapter playListViewBottomAdapter;


    public SearchSongAdapter(List<SongModel> songModelList, Context context) {
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
        LinearLayout removeFromPlaylist = dialog.findViewById(R.id.bts_remove);
        if (context instanceof MainActivity) {
            removeFromPlaylist.setVisibility(View.GONE);
        } else if (context instanceof PlayListActivity) {
            context = (PlayListActivity) context;
            removeFromPlaylist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((PlayListActivity) context).deleteSong(songModel.getId(), new ApiCallback<Integer>() {
                        @Override
                        public void onApiSuccess(Integer S) {
                            dialog.dismiss();
                            ((PlayListActivity) context).getDataToRecycleView();
                        }

                        @Override
                        public void onApiFailure(Throwable t) {
                            Toast.makeText(getContext(), "Có lỗi xảy ra", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }

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
        ImageUtils.setImageUrl(songModel.getImageUrl(), songImage, getContext());
//        songImage.setImageResource(songModel.getImage());
        TextView songName = dialog.findViewById(R.id.bts_txt_song);
        songName.setText(songModel.getSongName());
        TextView singer = dialog.findViewById(R.id.bts_txt_singer);
        singer.setText(songModel.getSinger());

        addPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Add Playlist", Toast.LENGTH_SHORT);
                dialog.dismiss();
                showDialogPlaylist(songModel);
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
        SharedPreferences sp = getContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String userId = sp.getString("userId", null);
        APIService.getService().CheckLikedSong(userId, songModel.getId()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200) {
                    if (response.body()) {
                        ((ImageView) dialog.findViewById(R.id.ic_liked)).setImageResource(R.drawable.full_hear);
                        ((TextView) dialog.findViewById(R.id.txt_liked)).setTextColor(getContext().getResources().getColor(R.color.green));
                    }
                    ((TextView) dialog.findViewById(R.id.txt_liked)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            likeSong(userId, songModel.getId(), new ApiCallback<Boolean>() {
                                @Override
                                public void onApiSuccess(Boolean S) {
                                    if (S) {
                                        ((ImageView) dialog.findViewById(R.id.ic_liked)).setImageResource(R.drawable.full_hear);
                                        ((TextView) dialog.findViewById(R.id.txt_liked)).setTextColor(getContext().getResources().getColor(R.color.green));
                                    } else {
                                        ((ImageView) dialog.findViewById(R.id.ic_liked)).setImageResource(R.drawable.empty_hear);
                                        ((TextView) dialog.findViewById(R.id.txt_liked)).setTextColor(getContext().getResources().getColor(R.color.white));
                                    }
                                }

                                @Override
                                public void onApiFailure(Throwable t) {

                                }
                            });
                        }
                    });

                    ((ImageView) dialog.findViewById(R.id.ic_liked)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            likeSong(userId, songModel.getId(), new ApiCallback<Boolean>() {
                                @Override
                                public void onApiSuccess(Boolean S) {
                                    if (S) {
                                        ((ImageView) dialog.findViewById(R.id.ic_liked)).setImageResource(R.drawable.full_hear);
                                        ((TextView) dialog.findViewById(R.id.txt_liked)).setTextColor(getContext().getResources().getColor(R.color.green));
                                    } else {
                                        ((ImageView) dialog.findViewById(R.id.ic_liked)).setImageResource(R.drawable.empty_hear);
                                        ((TextView) dialog.findViewById(R.id.txt_liked)).setTextColor(getContext().getResources().getColor(R.color.white));
                                    }
                                }

                                @Override
                                public void onApiFailure(Throwable t) {

                                }
                            });
                        }
                    });

                }
                dialog.show();
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });


    }


    private void likeSong(String userId, String songId, ApiCallback<Boolean> isLiked) {
        APIService.getService().LikedSong(userId, songId)
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.code() == 200) {
                            Gson gson = new Gson();
                            String json = gson.toJson(response.body());
                            Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());

                            isLiked.onApiSuccess(!(Boolean) map.get("is_liked"));
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Toast.makeText(getContext(), "Có lỗi xảy ra", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void showDialogPlaylist(SongModel songModel) {
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

        getListPlayList(new ApiCallback<List<PlayListModel>>() {
            @Override
            public void onApiSuccess(List<PlayListModel> S) {
                playListViewBottomAdapter = new PlayListViewBottomAdapter(
                        S, context, songModel
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
                        Log.e("monopoco", playListViewBottomAdapter.getIdsChosen().toString());
                        List<Observable<?>> request = new ArrayList<>();
                        if (playListViewBottomAdapter != null && playListViewBottomAdapter.getIdsChosen() != null) {
                            playListViewBottomAdapter.getIdsChosen().forEach(id -> {
                                PlayListAddSongModel playListAddSongModel = new PlayListAddSongModel(
                                        id, songModel.getId()
                                );
                                request.add(APIService.getService().addSongToPlayListObser(playListAddSongModel));
                            });
                        }
                        Observable.zip(
                                request,
                                new Function<Object[], Object>() {
                                    @Override
                                    public Object apply(Object[] objects) throws Exception {
                                        Log.e("monopco", "Hello bsajkfhasd");
                                        return objects;
                                    }
                                }
                        )
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                                new Consumer<Object>() {
                                    @Override
                                    public void accept(Object o) throws Exception {
                                        dialog.dismiss();
                                        Toast.makeText(getContext(), "Add song to playlists successfully", Toast.LENGTH_SHORT).show();
                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable e) throws Exception {
                                        e.printStackTrace();
                                        //Do something on error completion of requests
                                    }
                                }
                        );

                    }
                });

                dialog.show();
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }

            @Override
            public void onApiFailure(Throwable t) {

            }
        });


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

    private void getListPlayList(ApiCallback<List<PlayListModel>> callback) {
        SharedPreferences sp = getContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String userId = sp.getString("userId", null);
        APIService.getService().GetPlayListOfUser(userId).enqueue(new Callback<List<PlayListModel>>() {
            @Override
            public void onResponse(Call<List<PlayListModel>> call, Response<List<PlayListModel>> response) {
                if (response.code() == 200) {
                    callback.onApiSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<PlayListModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Server error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtSongName.setText(songModelList.get(position).getSongName());
        holder.txtSingerName.setText(songModelList.get(position).getSinger());
//        holder.songImage.setImageResource(songModelList.get(position).getImage());
        ImageUtils.setImageUrl(songModelList.get(position).getImageUrl(), holder.songImage, getContext());
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
