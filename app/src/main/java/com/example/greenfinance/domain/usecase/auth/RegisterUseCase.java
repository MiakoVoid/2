package com.example.greenfinance.domain.usecase.auth;

import androidx.lifecycle.LiveData;

import com.example.greenfinance.data.model.AuthResult;
import com.example.greenfinance.domain.repository.AuthRepository;

/**
 * 注册用例
 */
public class RegisterUseCase {
    private final AuthRepository authRepository;

    public RegisterUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    /**
     * 执行注册操作
     * @param username 用户名
     * @param password 密码
     * @param confirmPassword 确认密码
     * @return 注册结果
     */
    public LiveData<AuthResult> execute(String username, String password, String confirmPassword) {
        // 数据验证
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }

        if (username.length() < 3 || username.length() > 20) {
            throw new IllegalArgumentException("用户名长度必须在3-20位之间");
        }

        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("用户名只能包含字母、数字和下划线");
        }

        if (password == null || password.length() < 6 || password.length() > 20) {
            throw new IllegalArgumentException("密码长度必须在6-20位之间");
        }

        if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d).+$")) {
            throw new IllegalArgumentException("密码必须包含字母和数字");
        }

        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("两次输入的密码不一致");
        }

        // 执行注册
        return authRepository.register(username, password);
    }
}