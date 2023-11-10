package com.example.app_cocomo;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.biometric.BiometricManager;
import androidx.fragment.app.FragmentActivity;

import com.example.app_cocomo.auth.fingerprint.FingerprintActivity;
import com.example.app_cocomo.auth.fingerprint.FingerprintManager;
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
    private boolean resScreenlock;

    // 지문 등록
    private FragmentActivity fragmentActivity;
    private FingerprintManager fingerprintManager;

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


        // 지문 인증
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


        // 지문 등록
        Button btnFingerEnrolled = (Button)findViewById(R.id.home_etcbutton_fingerprint);
        btnFingerEnrolled.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                checkIfBiometricFeatureAvailable();
            }
        });


        // 스크린락
        Button btnScreenlock = (Button)findViewById(R.id.home_etcbutton_screenlock);
        btnScreenlock.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                boolean success = startScreenlock();
                Log.d(LogTag.HomeTag, "success: " + success);
                if (success)
                {
                    Toast.makeText(HomeActivity.this, userName + " PC의 스크린락이 실행되었습니다.", Toast.LENGTH_LONG).show();
                }
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


    private boolean startScreenlock()
    {
        restBuilder.startScreenlock(userId).enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if (response.isSuccessful())
                {
                    Log.d(LogTag.HomeTag, "스크린락 실행됨");
                    resScreenlock = true;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.d(LogTag.HomeTag, "Screenlock" + LogTag.FAILED + t.getMessage());
            }
        });

        return resScreenlock;
    }


    public boolean checkIfBiometricFeatureAvailable()
    {
        // BiometricManager : https://developer.android.com/reference/androidx/biometric/BiometricManager
        BiometricManager biometricManager = BiometricManager.from(this);
        Log.d(LogTag.FingerTag, "FingerprintManager checkIfBiometricFeatureAvailable");

        switch (biometricManager.canAuthenticate())
        {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d(LogTag.FingerTag, "App can authenticate using biometrics.");
                Toast.makeText(this, "이미 기기에 지문이 등록되어 있습니다.",Toast.LENGTH_SHORT).show();
                return false;

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                // 적합한 하드웨어가 없음
                Log.e(LogTag.FingerTag, "No biometric features available on this device.");
                Toast.makeText(this, "지문 등록을 지원하는 기기가 아닙니다.",Toast.LENGTH_SHORT).show();
                return false;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                // 하드웨어를 사용할 수 없음
                Log.e(LogTag.FingerTag, "Biometric features are currently unavailable.");
                Toast.makeText(this, "지문 등록을 사용할 수 없습니다.",Toast.LENGTH_SHORT).show();
                return false;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // 등록 된 생체 인식 또는 장치 자격 증명이 없음
                Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                fragmentActivity.startActivity(enrollIntent); // 액티비티를 열어주고 결과값 전달

                Log.e(LogTag.FingerTag, "There are no registered fingerprints.");
                Toast.makeText(this, "지문을 등록해주세요.",Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

}
