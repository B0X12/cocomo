package com.example.app_cocomo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbarmain);
        setSupportActionBar(tb);

        ActionBar home_tb = getSupportActionBar();

        getSupportActionBar().setIcon(R.drawable.home_top_logo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
