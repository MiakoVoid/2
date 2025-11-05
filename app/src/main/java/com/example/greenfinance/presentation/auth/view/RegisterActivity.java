package com.example.greenfinance.presentation.auth.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.greenfinance.data.model.AuthResult;
import com.example.greenfinance.databinding.ActivityRegisterBinding;
import com.example.greenfinance.presentation.auth.viewmodel.AuthViewModel;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 使用DataBinding加载布局
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 初始化ViewModel
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // 设置点击事件
        setupClickListeners();
    }

    private void setupClickListeners() {
        // 注册按钮点击事件
        binding.registerButton.setOnClickListener(v -> attemptRegister());

        // 跳转到登录页面
        binding.toLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void attemptRegister() {
        // 获取输入的用户名和密码
        String username = Objects.requireNonNull(binding.usernameInput.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.passwordInput.getText()).toString().trim();
        String confirmPassword = Objects.requireNonNull(binding.confirmPasswordInput.getText()).toString().trim();

        // 调用ViewModel进行注册
        androidx.lifecycle.LiveData<AuthResult> registerResult = 
            authViewModel.register(username, password, confirmPassword);
        
        registerResult.observe(this, result -> {
            if (result.isSuccess()) {
                // 注册成功，跳转到登录页面
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                // 注册失败，显示错误信息
                Toast.makeText(RegisterActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}