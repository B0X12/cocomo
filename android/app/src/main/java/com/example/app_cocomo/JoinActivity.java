package com.example.app_cocomo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class JoinActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // 뒤로가기 버튼
        Button btnBack = (Button)findViewById(R.id.join_back_btn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //회원가입 버튼
        Button buttonjoin = (Button)findViewById(R.id.buttonjoin_join);
        buttonjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_joinok = new Intent(JoinActivity.this, JoinOkActivity.class);
                startActivity(intent_joinok);

                finish();
            }
        });

    }
}
