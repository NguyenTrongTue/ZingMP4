package com.monopoco.musicmp4.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.monopoco.musicmp4.R;

public class RegisterActivity extends AppCompatActivity {

    private TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginText = findViewById(R.id.text_to_login);
        loginText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                navToLogin();
                return false;
            }
        });

    }

    private void navToLogin() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}