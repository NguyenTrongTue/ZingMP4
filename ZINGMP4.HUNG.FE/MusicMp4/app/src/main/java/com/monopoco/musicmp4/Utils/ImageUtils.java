package com.monopoco.musicmp4.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUtils {

    public static void setImageUrl(String url, ImageView imageView, Context context) {
        Glide.with(context).load(url.replace("https://localhost:7211", "http://10.0.2.2:5109")).into(imageView);
    }

}
