package com.example.greenfinance.data.remote.response;

import lombok.Data;

/**
 * 登录响应类
 */
@Data
public class LoginResponse {
    private long userId;
    private String username;
    private String avatarPath;
    private String token;
}