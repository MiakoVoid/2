package com.example.greenfinance.data.model;

import lombok.Data;

/**
 * 用户数据模型
 */
@Data
public class User {
    private long id;
    private String username;
    private String email;
    private String phone;
    private String avatarPath;
    private int status;
    
    public User() {
    }
    
    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }
}