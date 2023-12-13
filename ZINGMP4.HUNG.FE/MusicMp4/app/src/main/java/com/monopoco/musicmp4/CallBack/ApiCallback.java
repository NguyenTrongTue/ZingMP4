package com.monopoco.musicmp4.CallBack;

import com.monopoco.musicmp4.Models.SongModel;

public interface ApiCallback<S> {
    void onApiSuccess(S S);
    void onApiFailure(Throwable t);
}
