package com.example.app_cocomo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //로그인 버튼
        Button main_button_login = findViewById(R.id.main_button_login);
        main_button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_intent_join = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(main_intent_join);
            }
        });

        //회원가입 버튼
        Button main_button_join = findViewById(R.id.main_button_join);
        main_button_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_intent_join = new Intent(MainActivity.this, JoinActivity.class);
                startActivity(main_intent_join);
            }
        });

    }
}
