package com.example.app_cocomo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    Retrofit retrofit = new Retrofit.Builder()
            //서버 url 설정
            .baseUrl(TaskServer.ip)
            //데이터 파싱 설정
            .addConverterFactory(GsonConverterFactory.create())
            //객체정보 반환
            .build();

    //public ApiService apiService = retrofit.create(ApiService.class);
}
