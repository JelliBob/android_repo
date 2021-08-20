package com.com.myblog.config;

import com.com.myblog.model.User;

import lombok.Data;

//@AllArgsConstructor
//@NoArgsConstructor
@Data
public class SessionUser {
    public static User user;
    public static String token;
}