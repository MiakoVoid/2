package com.example.greenfinance.presentation.auth.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.greenfinance.data.model.AuthResult;
import com.example.greenfinance.data.model.User;
import com.example.greenfinance.data.repository.AuthRepositoryImpl;
import com.example.greenfinance.domain.repository.AuthRepository;
import com.example.greenfinance.domain.usecase.auth.LoginUseCase;
import com.example.greenfinance.domain.usecase.auth.RegisterUseCase;

public class AuthViewModel extends ViewModel {
    // 登录注册相关的ViewModel逻辑
    
    private final AuthRepository authRepository;
    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;
    
    public AuthViewModel() {
        // 初始化仓库和用例
        authRepository = new AuthRepositoryImpl();
        loginUseCase = new LoginUseCase(authRepository);
        registerUseCase = new RegisterUseCase(authRepository);
    }
    
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @param rememberMe 记住我
     */
    public LiveData<AuthResult> login(String username, String password, boolean rememberMe) {
        try {
            return loginUseCase.execute(username, password, rememberMe);
        } catch (IllegalArgumentException e) {
            // 创建一个立即返回错误的LiveData
            androidx.lifecycle.MutableLiveData<AuthResult> errorResult = new androidx.lifecycle.MutableLiveData<>();
            errorResult.setValue(new AuthResult(false, e.getMessage(), null));
            return errorResult;
        }
    }
    
    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @param confirmPassword 确认密码
     */
    public LiveData<AuthResult> register(String username, String password, String confirmPassword) {
        try {
            return registerUseCase.execute(username, password, confirmPassword);
        } catch (IllegalArgumentException e) {
            // 创建一个立即返回错误的LiveData
            androidx.lifecycle.MutableLiveData<AuthResult> errorResult = new androidx.lifecycle.MutableLiveData<>();
            errorResult.setValue(new AuthResult(false, e.getMessage(), null));
            return errorResult;
        }
    }
    
    /**
     * 获取当前用户
     * @return 当前用户
     */
    public LiveData<User> getCurrentUser() {
        return authRepository.getCurrentUser();
    }
    
    /**
     * 检查是否已登录
     * @return 是否已登录
     */
    public boolean isLoggedIn() {
        return authRepository.isLoggedIn();
    }
    
    /**
     * 检查token是否有效
     * @return token是否有效
     */
    public boolean isTokenValid() {
        return loginUseCase.isTokenValid();
    }
    
    /**
     * 获取保存的凭据
     * @return 保存的凭据
     */
    public AuthResult getSavedCredentials() {
        return loginUseCase.getSavedCredentials();
    }
}