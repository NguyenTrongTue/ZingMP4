package com.monopoco.musicmp4.Receiver;

import static com.monopoco.musicmp4.Classes.ApplicationClass.ACTION_NEXT;
import static com.monopoco.musicmp4.Classes.ApplicationClass.ACTION_PLAY;
import static com.monopoco.musicmp4.Classes.ApplicationClass.ACTION_PREVIOUS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.monopoco.musicmp4.Services.MediaPlayerService;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("monopoco", intent.getAction());

        String actionName = intent.getAction();

        Intent serviceIntent = new Intent(context, MediaPlayerService.class);

        if (actionName != null) {
            switch (actionName) {
                case ACTION_PLAY:
                    serviceIntent.putExtra("ActionName", "playPause");
                    context.startService(serviceIntent);
                    break;
                case ACTION_NEXT:
                    serviceIntent.putExtra("ActionName", "next");
                    context.startService(serviceIntent);
                    break;
                case ACTION_PREVIOUS:
                    serviceIntent.putExtra("ActionName", "previous");
                    context.startService(serviceIntent);
                    break;
            }
        }
    }
}
