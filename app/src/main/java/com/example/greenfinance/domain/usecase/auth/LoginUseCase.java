package com.example.greenfinance.domain.usecase.auth;

import androidx.lifecycle.LiveData;

import com.example.greenfinance.data.model.AuthResult;
import com.example.greenfinance.domain.repository.AuthRepository;

/**
 * 登录用例
 */
public class LoginUseCase {
    private final AuthRepository authRepository;

    public LoginUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    /**
     * 执行登录操作
     * @param username 用户名
     * @param password 密码
     * @param rememberMe 记住我
     * @return 登录结果
     */
    public LiveData<AuthResult> execute(String username, String password, boolean rememberMe) {
        // 数据验证
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }

        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("密码长度不能少于6位");
        }

        // 执行登录
        return authRepository.login(username, password, rememberMe);
    }
    
    /**
     * 检查是否有保存的凭据可以自动登录
     * @return 保存的凭据，如果不存在或已过期则返回null
     */
    public AuthResult getSavedCredentials() {
        return authRepository.getSavedCredentials();
    }
    
    /**
     * 检查token是否有效
     * @return token是否有效
     */
    public boolean isTokenValid() {
        return authRepository.isTokenValid();
    }
}