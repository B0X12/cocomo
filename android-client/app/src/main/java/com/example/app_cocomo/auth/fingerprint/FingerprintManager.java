package com.example.app_cocomo.auth.fingerprint;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import com.example.app_cocomo.rest.define.LogTag;

import static android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import java.util.concurrent.ExecutionException;

public class FingerprintManager {

    private static FingerprintManager instance = null;

    private Context context;
    private Callback callback;
    private FragmentActivity fragmentActivity;

    public static FingerprintManager getInstance(Context context)
    {
        Log.d(LogTag.FingerTag, "FingerprintManager getInstance");
        if (instance == null)
        {
            instance = new FingerprintManager();
        }
        instance.init(context);
        return instance;
    }

    private void init(Context context)
    {
        Log.d(LogTag.FingerTag, "FingerprintManager init");
        this.context = context;
        this.fragmentActivity = (FragmentActivity) context;
        this.callback = (Callback) context;
    }

    // 생체 인식 기능을 사용할 수 있는 상태인지 확인함
    public boolean checkIfBiometricFeatureAvailable()
    {
        // BiometricManager : https://developer.android.com/reference/androidx/biometric/BiometricManager
        BiometricManager biometricManager = BiometricManager.from(context);
        Log.d(LogTag.FingerTag, "FingerprintManager checkIfBiometricFeatureAvailable");

        switch (biometricManager.canAuthenticate())
        {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d(LogTag.FingerTag, "App can authenticate using biometrics.");
                //Toast.makeText(context, "App can authenticate using biometrics.",Toast.LENGTH_SHORT).show();
                return false;

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                // 적합한 하드웨어가 없음
                Log.e(LogTag.FingerTag, "No biometric features available on this device.");
                return false;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                // 하드웨어를 사용할 수 없음
                Log.e(LogTag.FingerTag, "Biometric features are currently unavailable.");
                return false;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // 등록 된 생체 인식 또는 장치 자격 증명이 없음
                Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                fragmentActivity.startActivity(enrollIntent); // 액티비티를 열어주고 결과값 전달
                Log.e(LogTag.FingerTag, "There are no registered fingerprints.");

                return true;
        }
        return false;
    }
    interface Callback
    {
        void onBiometricAuthenticationResult(String result, CharSequence errString) throws ExecutionException, InterruptedException;
    }



}