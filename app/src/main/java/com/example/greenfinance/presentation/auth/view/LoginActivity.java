package com.example.greenfinance.presentation.auth.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;

import com.example.greenfinance.R;
import com.example.greenfinance.common.base.BaseActivity;
import com.example.greenfinance.common.util.SecurePreferences;
import com.example.greenfinance.data.model.AuthResult;
import com.example.greenfinance.databinding.ActivityLoginBinding;
import com.example.greenfinance.presentation.auth.viewmodel.AuthViewModel;
import com.example.greenfinance.presentation.home.view.MainActivity;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化安全存储
        SecurePreferences.init(this);
        
        // 使用DataBinding加载布局
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 初始化ViewModel
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // 检查是否可以自动登录
        checkAutoLogin();
        
        // 设置点击事件
        setupClickListeners();
    }

    private void checkAutoLogin() {
        // 检查token是否有效
        if (authViewModel.isTokenValid()) {
            // Token有效，直接跳转到主页
            goToMainActivity();
            return;
        }
        
        // 检查是否有保存的凭据
        AuthResult savedCredentials = authViewModel.getSavedCredentials();
        if (savedCredentials != null && savedCredentials.isSuccess()) {
            // 使用保存的凭据自动登录
            String username = savedCredentials.getUser().getUsername();
            String password = SecurePreferences.getPassword();
            if (password != null) {
                autoLogin(username, password);
            }
        }
    }

    private void autoLogin(String username, String password) {
        // 显示自动登录进度
        binding.loginButton.setText("自动登录中...");
        binding.loginButton.setEnabled(false);

        // 调用ViewModel进行自动登录
        androidx.lifecycle.LiveData<AuthResult> loginResult = 
            authViewModel.login(username, password, true);
        
        loginResult.observe(this, new Observer<AuthResult>() {
            @Override
            public void onChanged(AuthResult result) {
                binding.loginButton.setText("登录");
                binding.loginButton.setEnabled(true);
                
                if (result.isSuccess()) {
                    // 自动登录成功，跳转到主页
                    Toast.makeText(LoginActivity.this, "自动登录成功", Toast.LENGTH_SHORT).show();
                    goToMainActivity();
                } else {
                    // 自动登录失败，显示错误信息
                    Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupClickListeners() {
        // 登录按钮点击事件
        binding.loginButton.setOnClickListener(v -> attemptLogin());

        // 跳转到注册页面
        binding.toRegisterButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void attemptLogin() {
        // 获取输入的用户名和密码
        String username = binding.usernameInput.getText().toString().trim();
        String password = binding.passwordInput.getText().toString().trim();

        // 验证输入
        if (TextUtils.isEmpty(username)) {
            binding.usernameInputLayout.setError("请输入用户名");
            return;
        } else {
            binding.usernameInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            binding.passwordInputLayout.setError("请输入密码");
            return;
        } else {
            binding.passwordInputLayout.setError(null);
        }

        // 显示登录进度
        binding.loginButton.setText("登录中...");
        binding.loginButton.setEnabled(false);

        // 调用ViewModel进行登录
        boolean rememberMe = binding.rememberMeCheckbox.isChecked();
        androidx.lifecycle.LiveData<AuthResult> loginResult = authViewModel.login(username, password, rememberMe);
        
        loginResult.observe(this, new Observer<AuthResult>() {
            @Override
            public void onChanged(AuthResult result) {
                binding.loginButton.setText("登录");
                binding.loginButton.setEnabled(true);
                
                if (result.isSuccess()) {
                    // 登录成功，跳转到主页
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    goToMainActivity();
                } else {
                    // 登录失败，显示错误信息
                    Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    private void goToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void handleAddButtonClick() {
        // 登录页面不需要处理添加按钮
    }
}