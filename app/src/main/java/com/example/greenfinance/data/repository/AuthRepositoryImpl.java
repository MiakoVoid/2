package com.example.greenfinance.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.greenfinance.common.util.SecurePreferences;
import com.example.greenfinance.data.model.AuthResult;
import com.example.greenfinance.data.model.User;
import com.example.greenfinance.data.remote.api.ApiClient;
import com.example.greenfinance.data.remote.api.AuthService;
import com.example.greenfinance.data.remote.response.BaseResponse;
import com.example.greenfinance.data.remote.response.LoginResponse;
import com.example.greenfinance.data.remote.response.RegisterResponse;
import com.example.greenfinance.domain.repository.AuthRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 认证仓库实现类
 */
public class AuthRepositoryImpl implements AuthRepository {
    private static final String TAG = "AuthRepositoryImpl";
    
    private final AuthService authService;
    private User currentUser;
    private boolean isLoggedIn = false;

    public AuthRepositoryImpl() {
        this.authService = ApiClient.getAuthService();
    }

    @Override
    public LiveData<AuthResult> login(String username, String password, boolean rememberMe) {
        MutableLiveData<AuthResult> result = new MutableLiveData<>();
        
        // 创建登录请求体
        AuthService.LoginRequest loginRequest = new AuthService.LoginRequest(username, password, rememberMe);
        Call<BaseResponse<LoginResponse>> call = authService.login(loginRequest);
        call.enqueue(new Callback<BaseResponse<LoginResponse>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<LoginResponse>> call, @NonNull Response<BaseResponse<LoginResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BaseResponse<LoginResponse> baseResponse = response.body();
                    if (baseResponse.isSuccess()) {
                        // 登录成功，创建用户对象
                        LoginResponse loginResponse = baseResponse.getData();
                        currentUser = new User(loginResponse.getUserId(), loginResponse.getUsername());
                        currentUser.setAvatarPath(loginResponse.getAvatarPath());
                        isLoggedIn = true;
                        
                        // 如果选择了"记住我"，保存凭据和token
                        if (rememberMe) {
                            SecurePreferences.saveCredentials(username, password, loginResponse.getToken(), true);
                        }
                        
                        result.setValue(new AuthResult(true, "登录成功", currentUser));
                    } else {
                        // 服务端返回错误
                        result.setValue(new AuthResult(false, baseResponse.getMessage(), null));
                    }
                } else {
                    // HTTP错误
                    result.setValue(new AuthResult(false, "登录失败，请稍后重试", null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<LoginResponse>> call, @NonNull Throwable t) {
                Log.e(TAG, "登录请求失败", t);
                result.setValue(new AuthResult(false, "网络连接失败，请检查网络设置", null));
            }
        });
        
        return result;
    }

    @Override
    public LiveData<AuthResult> register(String username, String password) {
        MutableLiveData<AuthResult> result = new MutableLiveData<>();
        
        // 创建注册请求体
        AuthService.RegisterRequest registerRequest = new AuthService.RegisterRequest(username, password);
        Call<BaseResponse<RegisterResponse>> call = authService.register(registerRequest);
        call.enqueue(new Callback<BaseResponse<RegisterResponse>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<RegisterResponse>> call, @NonNull Response<BaseResponse<RegisterResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BaseResponse<RegisterResponse> baseResponse = response.body();
                    if (baseResponse.isSuccess()) {
                        // 注册成功
                        RegisterResponse registerResponse = baseResponse.getData();
                        currentUser = new User(registerResponse.getUserId(), registerResponse.getUsername());
                        isLoggedIn = true;
                        
                        result.setValue(new AuthResult(true, "注册成功", currentUser));
                    } else {
                        // 服务端返回错误
                        result.setValue(new AuthResult(false, baseResponse.getMessage(), null));
                    }
                } else {
                    // HTTP错误
                    result.setValue(new AuthResult(false, "注册失败，请稍后重试", null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<RegisterResponse>> call, @NonNull Throwable t) {
                Log.e(TAG, "注册请求失败", t);
                result.setValue(new AuthResult(false, "网络连接失败，请检查网络设置", null));
            }
        });
        
        return result;
    }

    @Override
    public LiveData<Boolean> logout() {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        // 清除用户信息
        currentUser = null;
        isLoggedIn = false;
        // 清除保存的凭据
        SecurePreferences.clearCredentials();
        result.setValue(true);
        return result;
    }

    @Override
    public LiveData<User> getCurrentUser() {
        MutableLiveData<User> result = new MutableLiveData<>();
        result.setValue(currentUser);
        return result;
    }

    @Override
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    
    @Override
    public boolean isTokenValid() {
        // 检查token是否过期
        return !SecurePreferences.isTokenExpired();
    }
    
    @Override
    public String getSavedToken() {
        return SecurePreferences.getToken();
    }
    
    @Override
    public AuthResult getSavedCredentials() {
        // 检查是否有保存的凭据且未过期
        if (SecurePreferences.isRememberMe() && !SecurePreferences.isCredentialsExpired()) {
            String username = SecurePreferences.getUsername();
            String password = SecurePreferences.getPassword();
            if (username != null && password != null) {
                // 创建一个临时用户对象
                User user = new User(0, username);
                return new AuthResult(true, "使用保存的凭据", user);
            }
        }
        return null;
    }
}