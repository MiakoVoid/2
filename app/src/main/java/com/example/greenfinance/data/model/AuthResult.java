package com.example.greenfinance.data.model;

import lombok.Data;

/**
 * 认证结果数据模型
 */
@Data
public class AuthResult {
    private boolean success;
    private String message;
    private User user;

    public AuthResult() {
    }

    public AuthResult(boolean success, String message, User user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }
}