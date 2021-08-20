package com.com.myblog.service;

import com.com.myblog.controller.dto.CMRespDto;
import com.com.myblog.controller.dto.JoinDto;
import com.com.myblog.controller.dto.LoginDto;
import com.com.myblog.model.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("/join")
    Call<CMRespDto<User>> join(@Body JoinDto joinDto);

    @POST("/login")
    Call<CMRespDto<User>> login(@Body LoginDto loginDto);

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.0.6:8080") // 내 본인 ip로 수정하기
            .build();

    UserService service = retrofit.create(UserService.class);
}
