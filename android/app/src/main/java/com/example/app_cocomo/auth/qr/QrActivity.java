package com.example.app_cocomo.auth.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.app_cocomo.HomeActivity;
import com.example.app_cocomo.LoginActivity;
import com.example.app_cocomo.R;
import com.example.app_cocomo.rest.RestBuilder;
import com.example.app_cocomo.rest.define.AuthUser;
import com.example.app_cocomo.rest.define.DefinePath;
import com.example.app_cocomo.rest.define.LogTag;
import com.example.app_cocomo.rest.define.Status;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.app_cocomo.HomeActivity;
import com.example.app_cocomo.R;
import com.example.app_cocomo.rest.define.LogTag;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class QrActivity extends AppCompatActivity {

    private String userId;
    private int authResult;

    private RestBuilder restBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);


        // REST API 통신
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DefinePath.DEFAULT) // localhost:1208/
                .addConverterFactory(GsonConverterFactory.create()) // JSON -> Java 객체 변환
                .build();

        restBuilder = retrofit.create(RestBuilder.class);


        // Home -> Qr
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");


        IntentIntegrator QR = new IntentIntegrator(this);
        QR.setBeepEnabled(true); // QR 스캔 시 소리를 낼 것인지
        QR.setOrientationLocked(true); // 화면 회전을 막을 것인지
        QR.setPrompt("QR을 스캔해주세요."); // QR코드 스캔 액티비티 하단에 띄울 텍스트를 입력

        QR.initiateScan();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult != null)
        {
            if (intentResult.getContents() == null) // QR 코드에 주소가 없거나, 뒤로가기 클릭 시
            {
                Toast.makeText(this, "인증을 취소하였습니다.", Toast.LENGTH_LONG).show();
                Log.d(LogTag.QrTag, LogTag.FAILED);

                onBackPressed();
            }
            else
            {
                try {
                    // String형으로 변환되어 넘어온 json Data를 json 형식으로 변환
                    JSONObject jsonObject = new JSONObject(intentResult.getContents());

                    if (userId.equals(jsonObject.getString("userId")))
                    {
                        Log.d(LogTag.QrTag, LogTag.SUCCESS + userId.equals(jsonObject.getString("userId")));
                        authResult = Status.AUTH_SUCCESS;
                    }
                    else
                    {
                        Log.d(LogTag.QrTag, LogTag.INVAILD);
                        authResult = Status.AUTH_FAILED;
                    }
                } catch (JSONException e) {
                    authResult = Status.AUTH_ERROR;
                    Log.d(LogTag.QrTag, LogTag.FAILED + e.getMessage());
                    e.printStackTrace();
                }
            }

            // REST API로 결과 반환
            sendRequest(authResult);
            finish();
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void sendRequest(int authResult)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("authQrResult", String.valueOf(authResult));
        Log.d(LogTag.QrTag, "POST Body: " + map.toString());

        restBuilder.authQrResult(userId, map).enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if (response.isSuccessful())
                {
                    Log.d(LogTag.QrTag, "sendRequest()" + LogTag.SUCCESS);
                    return;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.d(LogTag.QrTag, "REST API" + LogTag.FAILED + t.getMessage());
            }
        });
    }

}