package com.example.greenfinance.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * 安全的SharedPreferences工具类
 * 用于存储加密的用户凭据和token
 */
public class SecurePreferences {
    private static final String TAG = "SecurePreferences";
    private static final String PREFS_NAME = "greenfinance_secure_prefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_LOGIN_TIME = "login_time";
    private static final String KEY_REMEMBER_ME = "remember_me";
    //7 * 24 * 60 * 60 * 1000L
    private static final Long KEY_EXPIRED_TIME = 7 * 24 * 60 * 60 * 1000L; // 7天过期时间;
    
    private static SharedPreferences sharedPreferences;
    private static Context appContext; // 保存应用上下文
    
    public static void init(Context context) {
        if (context == null) {
            Log.e(TAG, "Context is null, cannot initialize SecurePreferences");
            return;
        }
        
        // 保存应用上下文引用
        appContext = context.getApplicationContext();
        
        try {
            MasterKey masterKey = new MasterKey.Builder(appContext, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
            
            sharedPreferences = EncryptedSharedPreferences.create(
                    appContext,
                    PREFS_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
            Log.d(TAG, "EncryptedSharedPreferences initialized successfully");
        } catch (GeneralSecurityException | IOException e) {
            Log.e(TAG, "Failed to initialize EncryptedSharedPreferences, falling back to regular SharedPreferences", e);
            // 如果加密SharedPreferences不可用，回退到普通SharedPreferences
            sharedPreferences = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
    }
    
    public static void saveCredentials(String username, String password, String token, boolean rememberMe) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_USERNAME, username);
            editor.putString(KEY_PASSWORD, password);
            editor.putString(KEY_TOKEN, token);
            editor.putLong(KEY_LOGIN_TIME, System.currentTimeMillis());
            editor.putBoolean(KEY_REMEMBER_ME, rememberMe);
            editor.apply();
        } else {
            Log.w(TAG, "SharedPreferences is null, cannot save credentials");
        }
    }
    
    public static String getUsername() {
        return sharedPreferences != null ? sharedPreferences.getString(KEY_USERNAME, null) : null;
    }
    
    public static String getPassword() {
        return sharedPreferences != null ? sharedPreferences.getString(KEY_PASSWORD, null) : null;
    }
    
    public static String getToken() {
        return sharedPreferences != null ? sharedPreferences.getString(KEY_TOKEN, null) : null;
    }
    
    public static long getLoginTime() {
        return sharedPreferences != null ? sharedPreferences.getLong(KEY_LOGIN_TIME, 0) : 0;
    }
    
    public static boolean isRememberMe() {
        return sharedPreferences != null && sharedPreferences.getBoolean(KEY_REMEMBER_ME, false);
    }
    
    public static boolean isTokenExpired() {
        long loginTime = getLoginTime();
        // Token 7天过期
        return System.currentTimeMillis() - loginTime > KEY_EXPIRED_TIME;
    }
    
    public static boolean isCredentialsExpired() {
        long loginTime = getLoginTime();
        // 凭据7天过期
        return System.currentTimeMillis() - loginTime > KEY_EXPIRED_TIME;
    }
    
    public static void clearCredentials() {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(KEY_USERNAME);
            editor.remove(KEY_PASSWORD);
            editor.remove(KEY_TOKEN);
            editor.remove(KEY_LOGIN_TIME);
            editor.remove(KEY_REMEMBER_ME);
            editor.apply();
        }
    }
    
    // 添加获取应用上下文的方法
    public static Context getAppContext() {
        return appContext;
    }
}