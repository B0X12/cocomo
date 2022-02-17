package com.example.app_cocomo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cocomo.rest.RestBuilder;
import com.example.app_cocomo.rest.define.DefinePath;
import com.example.app_cocomo.rest.define.LogTag;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JoinActivity extends AppCompatActivity {

    // Cureent Time
    private Date date = new Date(System.currentTimeMillis());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String currentTime = dateFormat.format(date);

    // User Data
    private EditText etUserId;
    private EditText etPasswd;
    private EditText etPasswdChk;
    private EditText etUserName;
    private EditText etEmail;
    private EditText etPhone;
    private Button btnJoin;

    private boolean formChkRes = false;
    private boolean sendReqRes = false;

    private RestBuilder restBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        // REST API 통신
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DefinePath.DEFAULT) // localhost:1208/
                .addConverterFactory(GsonConverterFactory.create()) // JSON -> Java 객체 변환
                .build();

        restBuilder = retrofit.create(RestBuilder.class);


        // 뒤로가기 버튼
        Button btnBack = (Button)findViewById(R.id.join_back_btn);
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });


        // 회원가입 버튼
        btnJoin = (Button)findViewById(R.id.btnJoin_join);
        btnJoin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (etFormCheck()) // 입력 폼 확인
                {
                    Log.d(LogTag.JoinTag, "etFormCheck()" + LogTag.SUCCESS);

                    if (sendRequest()) // rest api 요청 성공시에만
                    {
                        Log.d(LogTag.JoinTag, "sendRequest()" + LogTag.SUCCESS);
                        Intent intentJoinok = new Intent(JoinActivity.this, JoinOkActivity.class);
                        startActivity(intentJoinok);

                        finish();
                    }
                    else
                    {
                        Toast.makeText(JoinActivity.this, "잘못된 요청입니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean etFormCheck()
    {
        etPasswdChk = (EditText)findViewById(R.id.editjoin_pwchk);
        etUserId = (EditText)findViewById(R.id.editText_id);
        etPasswd = (EditText)findViewById(R.id.editText_pw);
        etUserName = (EditText)findViewById(R.id.editjoin_name);
        etEmail = (EditText)findViewById(R.id.editjoin_email);
        etPhone = (EditText)findViewById(R.id.editjoin_phone);

        if (etUserId.getText().toString().length() < 4)
        {
            Toast.makeText(JoinActivity.this, "ID는 4글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
            etUserId.requestFocus();
            formChkRes = false;
        }
        else if (etPasswd.getText().toString().length() < 5)
        {
            Toast.makeText(JoinActivity.this, "비밀번호는 5글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
            etPasswd.requestFocus();
            formChkRes = false;
        }
        else if (etPasswdChk.getText().toString().length() == 0)
        {
            Toast.makeText(JoinActivity.this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
            etPasswdChk.requestFocus();
            formChkRes = false;
        }
        else if (!etPasswdChk.getText().toString().equals(etPasswd.getText().toString()))
        {
            Toast.makeText(JoinActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            etPasswdChk.setText("");
            etPasswdChk.requestFocus();
            formChkRes = false;
        }
        else if (etUserName.getText().toString().length() < 2)
        {
            Toast.makeText(JoinActivity.this, "이름은 2글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
            etUserName.requestFocus();
            formChkRes = false;
        }
        else if (etEmail.getText().toString().length() == 0)
        {
            Toast.makeText(JoinActivity.this, "이메일 주소를 입력해주세요.", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            formChkRes = false;
        }
        else if (etPhone.getText().toString().length() == 0)
        {
            Toast.makeText(JoinActivity.this, "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            etPhone.requestFocus();
            formChkRes = false;
        }
        else
        {
            formChkRes = true;
        }

        return formChkRes;
    }

    private boolean sendRequest()
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", etUserId.getText().toString());
        map.put("passwd", etPasswd.getText().toString());
        map.put("userName", etUserName.getText().toString());
        map.put("email", etEmail.getText().toString());
        map.put("phone", etPhone.getText().toString());
        map.put("joinDate", currentTime);

        Log.d(LogTag.JoinTag, "POST Body: " + map.toString());

        restBuilder.signUpUser(map).enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if (response.isSuccessful())
                {
                    Log.d(LogTag.JoinTag, "REST API" + LogTag.SUCCESS);
                    sendReqRes = true;
                } else {
                    Log.d(LogTag.JoinTag, "REST API" + LogTag.FAILED + String.valueOf(response.code()));
                    sendReqRes = false;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.d(LogTag.JoinTag, "REST API" + LogTag.FAILED + t.getMessage());
                sendReqRes = false;
            }
        });

        return sendReqRes;
    }

}