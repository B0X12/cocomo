package com.example.app_cocomo.auth.qr;

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


public class QrActivity extends AppCompatActivity {

    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

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
                Log.d(LogTag.QRTag, LogTag.FAILED);

                onBackPressed();
            }
            else
            {
                try {
                    // String형으로 변환되어 넘어온 json Data를 json 형식으로 변환
                    JSONObject jsonObject = new JSONObject(intentResult.getContents());

                    if (userId.equals(jsonObject.getString("userId")))
                    {
                        Toast.makeText(this, "인증에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                        Log.d(LogTag.QRTag, LogTag.SUCCESS + userId.equals(jsonObject.getString("userId")));
                    }
                    else
                    {
                        Toast.makeText(this, "유효하지 않은 QR입니다.", Toast.LENGTH_SHORT).show();
                        Log.d(LogTag.QRTag, LogTag.INVAILD);
                    }
                } catch (JSONException e) {
                    Log.d(LogTag.QRTag, LogTag.FAILED + e.getMessage());
                    e.printStackTrace();
                }
            }

            finish();
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}