package com.example.app_cocomo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cocomo.rest.RestBuilder;
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

    private Date date = new Date(System.currentTimeMillis());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String currentTime = dateFormat.format(date);

    private RestBuilder restBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.200.156:8080/") // localhost:1208/
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
        Button btnJoin = (Button)findViewById(R.id.btnJoin_join);
        btnJoin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                signUpTask();

                Intent intent_joinok = new Intent(JoinActivity.this, JoinOkActivity.class);
                startActivity(intent_joinok);

                finish();
            }
        });
    }

    private void signUpTask()
    {
        EditText userId = (EditText)findViewById(R.id.editText_id);
        EditText passwd = (EditText)findViewById(R.id.editText_pw);
        EditText userName = (EditText)findViewById(R.id.editjoin_name);
        EditText email = (EditText)findViewById(R.id.editjoin_email);
        EditText phone = (EditText)findViewById(R.id.editjoin_phone);

        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId.getText().toString());
        map.put("passwd", passwd.getText().toString());
        map.put("userName", userName.getText().toString());
        map.put("email", email.getText().toString());
        map.put("phone", phone.getText().toString());
        map.put("joinDate", currentTime);

        restBuilder.signUpUser(map).enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if (response.isSuccessful())
                {
                    Log.d(LogTag.JoinTag, "Success");

                } else {
                    Log.d(LogTag.JoinTag, String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.d(LogTag.JoinTag, t.getMessage());
            }
        });
    }

}