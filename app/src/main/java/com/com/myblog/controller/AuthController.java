package com.com.myblog.controller;

import com.com.myblog.controller.dto.CMRespDto;
import com.com.myblog.controller.dto.JoinDto;
import com.com.myblog.controller.dto.LoginDto;
import com.com.myblog.model.User;
import com.com.myblog.service.UserService;

import retrofit2.Call;


public class AuthController {
    private static final String TAG = "AuthController";
    private UserService userService = UserService.service;

    public Call<CMRespDto<User>> join(JoinDto joinDto){
        return userService.join(joinDto);
    }

    public Call<CMRespDto<User>> login(LoginDto loginDto){
        return userService.login(loginDto);
    }
}
