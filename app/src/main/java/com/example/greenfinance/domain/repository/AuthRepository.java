package com.example.greenfinance.domain.repository;

import androidx.lifecycle.LiveData;

import com.example.greenfinance.data.model.AuthResult;
import com.example.greenfinance.data.model.User;

/**
 * 认证仓库接口
 */
public interface AuthRepository {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @param rememberMe 记住我
     * @return 登录结果LiveData
     */
    LiveData<AuthResult> login(String username, String password, boolean rememberMe);

    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @return 注册结果LiveData
     */
    LiveData<AuthResult> register(String username, String password);

    /**
     * 用户登出
     * @return 登出结果
     */
    LiveData<Boolean> logout();

    /**
     * 获取当前用户
     * @return 当前用户信息
     */
    LiveData<User> getCurrentUser();

    /**
     * 检查是否已登录
     * @return 是否已登录
     */
    boolean isLoggedIn();
    
    /**
     * 检查token是否有效
     * @return token是否有效
     */
    boolean isTokenValid();
    
    /**
     * 获取保存的token
     * @return 保存的token
     */
    String getSavedToken();
    
    /**
     * 获取保存的凭据
     * @return 保存的凭据，如果不存在或已过期则返回null
     */
    AuthResult getSavedCredentials();
}