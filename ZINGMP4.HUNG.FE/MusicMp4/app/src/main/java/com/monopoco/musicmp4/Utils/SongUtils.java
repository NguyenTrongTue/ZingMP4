package com.monopoco.musicmp4.Utils;

public class SongUtils {

    public static String getSongResource(String src) {
        return src.replace("https://localhost:7211", "http://10.0.2.2:5109");
    }
}
