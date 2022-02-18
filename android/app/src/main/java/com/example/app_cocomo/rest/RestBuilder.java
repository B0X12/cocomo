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
import com.example.app_cocomo.rest.define.AuthUser;
import com.example.app_cocomo.rest.define.DefinePath;
import com.example.app_cocomo.rest.define.ErrorResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RestBuilder {

    /*
     * User
     */

    @POST("cocomo/join") // 가입
    Call<Void> signUpUser(@Body HashMap<String, String> user);

    @POST("cocomo/login") // 로그인
    Call<Void> signInUser(@Body HashMap<String, String> user);
    // token값을 받아옴

    @GET("cocomo/{userId}")
    Call<User> findUserName(@Path("userId") String userId);



    /*
     * Auth
     */

    @POST("/auth/qr/{userId}") // QR 결과 반환
    Call<Void> authQrResult(@Path("userId") String userId, @Body HashMap<String, String> authUser);

    @POST("/auth/finger/{userId}") // 지문인증 결과 반환
    Call<Void> authFingerResult(@Path("userId") String userId, @Body HashMap<String, String> authUser);

}