package com.example.app_cocomo.auth.otp;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import java.util.Date;
import java.util.HashMap;

import com.example.app_cocomo.R;
import com.example.app_cocomo.rest.RestBuilder;
import com.example.app_cocomo.rest.define.AuthUser;
import com.example.app_cocomo.rest.define.DefinePath;
import com.example.app_cocomo.rest.define.LogTag;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OtpActivity extends AppCompatActivity {

    private static final int MESSAGE_TIMER_START = 100;
    private static final int MESSAGE_TIMER_ING = 101;

    TimerHandler timerHandler = null;

    TextView tvOtpCode;
    TextView tvTimer;
    Button btnBack;

    // otp Data
    private String userId;
    private String otp_key, otp_code;

    private AuthUser authUser;
    private RestBuilder restBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        timerHandler = new TimerHandler();

        tvOtpCode = (TextView)findViewById(R.id.tvOtp_code); // OTP 코드
        tvTimer = (TextView)findViewById(R.id.tvOtp_timer); // 남은 시간


        // REST API 통신
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DefinePath.DEFAULT) // localhost:1208/
                .addConverterFactory(GsonConverterFactory.create()) // JSON -> Java 객체 변환
                .build();

        restBuilder = retrofit.create(RestBuilder.class);


        // userID setting
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");


        // otp 값 가져와서 start
        getOtpKey();
        OtpService otp = new OtpService();
        try {
            otp_code = otp.generateOtp(otp_key);
            tvOtpCode.setText(otp_code);
        }
        catch (Exception e) { }


        // 스레드 시작 (유효시간 타이머 표시)
        timerHandler.sendEmptyMessage(MESSAGE_TIMER_START);


        // back
        btnBack = (Button)findViewById(R.id.btnOtp_back);
        btnBack.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                timerHandler.removeMessages(MESSAGE_TIMER_ING);
                finish();
            }
        });
    }

    private class TimerHandler extends Handler {
        int count = 0;
        long wave, next_value;

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch(msg.what)
            {
                case MESSAGE_TIMER_START:

                    wave = new Date().getTime() / 30000;
                    next_value = (wave+1)*30000 - new Date().getTime();
                    this.sendEmptyMessage(MESSAGE_TIMER_ING);
                    break;

                case MESSAGE_TIMER_ING:

                    next_value = (wave+1)*30000 - new Date().getTime();
                    if (next_value < 1000)
                    {
                        wave = new Date().getTime() / 30000;
                        next_value = (wave+1)*30000 - new Date().getTime();

                        // id에 임시값을 넣어놓은 상태. rest api로 해당 유저의 id 가져와서 값 넣어줄 것.
                        OtpService otp = new OtpService();
                        try {
                            otp_code = otp.generateOtp(otp_key);
                            tvOtpCode.setText(otp_code);
                            //Log.d("MyTag", otp_code);
                        }
                        catch (Exception e) {}
                    }
                    tvTimer.setText("남은 시간 : "+(next_value/1000)+"초");
                    this.sendEmptyMessageDelayed(MESSAGE_TIMER_ING, 1000);

                    break;
            }
        }
    }


    private void getOtpKey()
    {
        restBuilder.getOtpKey(userId).enqueue(new Callback<AuthUser>()
        {
            @Override
            public void onResponse(Call<AuthUser> call, Response<AuthUser> response)
            {
                if (response.isSuccessful())
                {
                    authUser = response.body();
                    otp_key = authUser.getOtpKey();

                    Log.d(LogTag.QrTag, "otp_key: " + otp_key);
                }
            }

            @Override
            public void onFailure(Call<AuthUser> call, Throwable t)
            {
                Log.d(LogTag.QrTag, "REST API" + LogTag.FAILED + t.getMessage());
            }
        });
    }
}