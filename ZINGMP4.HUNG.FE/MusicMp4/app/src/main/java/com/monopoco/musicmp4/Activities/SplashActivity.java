package com.monopoco.musicmp4.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.monopoco.musicmp4.R;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;

    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent;
                SharedPreferences sp1= getSharedPreferences("Login", MODE_PRIVATE);
                Boolean isLogin = Boolean.valueOf(sp1.getString("isLogin", null));

                if (isLogin) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, SignInActivity.class);

                }
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}