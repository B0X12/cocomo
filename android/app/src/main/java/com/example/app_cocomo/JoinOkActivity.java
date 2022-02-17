package com.example.app_cocomo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class JoinOkActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinok);

        Button buttonjoinok_login = (Button) findViewById(R.id.buttonjoinok_login);
        buttonjoinok_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intentLogin = new Intent(JoinOkActivity.this, LoginActivity.class);
                startActivity(intentLogin);

                finish();
            }
        });

    }
}
