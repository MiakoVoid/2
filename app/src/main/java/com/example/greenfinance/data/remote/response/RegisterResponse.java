package com.example.greenfinance.data.remote.response;

import lombok.Data;

/**
 * 注册响应类
 */
@Data
public class RegisterResponse {
    private long userId;
    private String username;
    private String token;
}