package com.example.app_cocomo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.app_cocomo.auth.fingerprint.FingerprintActivity;
import com.example.app_cocomo.auth.otp.OtpActivity;
import com.example.app_cocomo.auth.qr.QrActivity;
import com.example.app_cocomo.rest.RestBuilder;
import com.example.app_cocomo.rest.define.DefinePath;
import com.example.app_cocomo.rest.define.LogTag;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private User userData;

    private TextView tvUserName;
    private String userId;
    private String userName;
    private int userAuthResult;

    private RestBuilder restBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // REST API 통신
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DefinePath.DEFAULT) // localhost:1208/
                .addConverterFactory(GsonConverterFactory.create()) // JSON -> Java 객체 변환
                .build();

        restBuilder = retrofit.create(RestBuilder.class);


        // Toolbar Setting
        Toolbar tb = (Toolbar) findViewById(R.id.toolbarmain);
        setSupportActionBar(tb);
        ActionBar home_tb = getSupportActionBar();
        // getSupportActionBar().setIcon(R.drawable.home_top_logo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // userName Setting, Toast Message
        tvUserName = (TextView)findViewById(R.id.tvHome_userName);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userNameSetting();


        // OTP
        Button btnOtp = (Button)findViewById(R.id.home_button_otp);
        btnOtp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intentOtp = new Intent(HomeActivity.this, OtpActivity.class);
                intentOtp.putExtra("userId", userId);
                startActivity(intentOtp);
            }
        });


        // QR 스캔
        Button btnQr = (Button)findViewById(R.id.home_button_qr);
        btnQr.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intentQr = new Intent(HomeActivity.this, QrActivity.class);
                intentQr.putExtra("userId", userId);
                startActivity(intentQr);
            }
        });


        // 지문인증
        Button btnFingerprint = (Button)findViewById(R.id.home_button_bio);
        btnFingerprint.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intentFinger = new Intent(HomeActivity.this, FingerprintActivity.class);
                intentFinger.putExtra("userId", userId);
                startActivity(intentFinger);
            }
        });

    }


    private void userNameSetting()
    {
        restBuilder.findUserName(userId).enqueue(new Callback<User>()
        {
            @Override
            public void onResponse(Call<User> call, Response<User> response)
            {
                if (response.isSuccessful())
                {
                    userData = response.body();
                    userName = userData.getUserName();
                    tvUserName.setText(userName);

                    Log.d(LogTag.HomeTag, userData.toString());
                    Toast.makeText(HomeActivity.this, userName + "님 반갑습니다!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t)
            {
                Log.d(LogTag.HomeTag, "REST API" + LogTag.FAILED + t.getMessage());
            }
        });
    }

}
