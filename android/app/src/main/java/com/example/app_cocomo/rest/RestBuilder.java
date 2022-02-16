package com.example.app_cocomo.rest;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.example.app_cocomo.User;
import com.example.app_cocomo.rest.define.DefinePath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RestBuilder {

    @POST("jpa/users") // 최종
    Call<Void> signUpUser(@Body HashMap<String, String> user);

    @GET("users/{id}") // 선택 유저 조회
    Call<List<User>> searchUser(@Path("id") int id);


}

