package com.example.greenfinance.presentation.auth.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;

import com.example.greenfinance.R;
import com.example.greenfinance.common.base.BaseActivity;
import com.example.greenfinance.data.model.AuthResult;
import com.example.greenfinance.databinding.ActivityRegisterBinding;
import com.example.greenfinance.presentation.auth.viewmodel.AuthViewModel;

public class RegisterActivity extends BaseActivity {

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
        String username = binding.usernameInput.getText().toString().trim();
        String password = binding.passwordInput.getText().toString().trim();
        String confirmPassword = binding.confirmPasswordInput.getText().toString().trim();

        // 调用ViewModel进行注册
        androidx.lifecycle.LiveData<AuthResult> registerResult = 
            authViewModel.register(username, password, confirmPassword);
        
        registerResult.observe(this, new Observer<AuthResult>() {
            @Override
            public void onChanged(AuthResult result) {
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
            }
        });
    }

    @Override
    protected void handleAddButtonClick() {
        // 注册页面不需要处理添加按钮
    }
}