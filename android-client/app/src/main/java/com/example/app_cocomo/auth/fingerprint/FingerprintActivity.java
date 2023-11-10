package com.example.app_cocomo.auth.fingerprint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.app_cocomo.HomeActivity;
import com.example.app_cocomo.R;
import com.example.app_cocomo.rest.RestBuilder;
import com.example.app_cocomo.rest.define.DefinePath;
import com.example.app_cocomo.rest.define.LogTag;
import com.example.app_cocomo.rest.define.Status;

import java.util.HashMap;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FingerprintActivity extends AppCompatActivity {

    // 지문 인증
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    private String userId;
    private int authResult;

    private RestBuilder restBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);


        // REST API 통신
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DefinePath.DEFAULT) // localhost:1208/
                .addConverterFactory(GsonConverterFactory.create()) // JSON -> Java 객체 변환
                .build();

        restBuilder = retrofit.create(RestBuilder.class);


        // Home -> Finger
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        Log.d(LogTag.FingerTag, userId);


        executor = ContextCompat.getMainExecutor(this);

        biometricPrompt = new BiometricPrompt(this,
                executor, new BiometricPrompt.AuthenticationCallback()
        {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString)
            {
                super.onAuthenticationError(errorCode, errString);
                // Toast.makeText(getApplicationContext(), R.string.auth_error_message, Toast.LENGTH_SHORT).show();
                authResult = Status.AUTH_ERROR;

                Intent intentHome = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intentHome);
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                authResult = Status.AUTH_SUCCESS;
                sendRequest(authResult);
            }

            @Override
            public void onAuthenticationFailed()
            {
                super.onAuthenticationFailed();
                authResult = Status.AUTH_FAILED;
                sendRequest(authResult);
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("지문 인증")
                .setSubtitle("기기에 등록된 지문을 이용하여 지문을 인증해주세요.")
                .setNegativeButtonText("취소")
                .setDeviceCredentialAllowed(false)
                .build();

        biometricPrompt.authenticate(promptInfo);  // 사용자에게 생체 인식 프롬프트를 표시함

    }

    private void sendRequest(int authResult)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("authFingerResult", String.valueOf(authResult));
        Log.d(LogTag.FingerTag, "POST Body: " + map.toString());

        restBuilder.authFingerResult(userId, map).enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if (response.isSuccessful())
                {
                    Log.d(LogTag.FingerTag, "sendRequest()" + LogTag.SUCCESS);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.d(LogTag.FingerTag, "REST API" + LogTag.FAILED + t.getMessage());
                finish();
            }
        });
    }
}