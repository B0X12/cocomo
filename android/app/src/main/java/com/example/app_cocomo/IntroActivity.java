package com.example.app_cocomo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Handler intro_handler = new Handler(Looper.getMainLooper());
        intro_handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intro_intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intro_intent);
                finish();
            }
        }, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
