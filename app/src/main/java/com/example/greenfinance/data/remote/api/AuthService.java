package com.example.greenfinance.data.remote.api;

import com.example.greenfinance.data.remote.response.BaseResponse;
import com.example.greenfinance.data.remote.response.LoginResponse;
import com.example.greenfinance.data.remote.response.RegisterResponse;

import lombok.Data;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 认证相关API接口
 */
public interface AuthService {
    /**
     * 用户登录
     * @param loginRequest 登录请求体
     * @return 登录响应
     */
    @POST("auth/login")
    Call<BaseResponse<LoginResponse>> login(@Body LoginRequest loginRequest);

    /**
     * 用户注册
     * @param registerRequest 注册请求体
     * @return 注册响应
     */
    @POST("auth/register")
    Call<BaseResponse<RegisterResponse>> register(@Body RegisterRequest registerRequest);
    
    /**
     * 登录请求体
     */
    @Data
    class LoginRequest {
        private String username;
        private String password;
        private boolean rememberMe;
        public LoginRequest(String username, String password, boolean rememberMe) {
            this.username = username;
            this.password = password;
            this.rememberMe = rememberMe;

        }
    }

    /**
     * 注册请求体
     */
    @Data
    class RegisterRequest {
        private String username;
        private String password;
        public RegisterRequest(String username, String password) {
            this.username = username;
            this.password = password;

        }
    }
}