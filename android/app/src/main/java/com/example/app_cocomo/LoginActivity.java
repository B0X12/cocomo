package com.example.app_cocomo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //회원가입 버튼
        Button buttonjoin = (Button) findViewById(R.id.btn_join);
        buttonjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_join = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent_join);

            }
        });

        //로그인 버튼
        Button buttonlogin = (Button) findViewById(R.id.btn_login);
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_main = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent_main);

            }
        });

        /*
        //지문
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        R.string.auth_error_message, Toast.LENGTH_SHORT).show();

                Intent intent_main = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent_main);
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        R.string.auth_success_message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), R.string.auth_fail_message, Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("지문 인증")
                .setSubtitle("기기에 등록된 지문을 이용하여 지문을 인증해주세요.")
                .setNegativeButtonText("취소")
                .setDeviceCredentialAllowed(false)
                .build();

        //  사용자가 다른 인증을 이용하길 원할 때 추가하기

        Button biometricLoginButton = findViewById(R.id.buttonhome_bio);
        biometricLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biometricPrompt.authenticate(promptInfo);

            }
        });
        */


    }
}