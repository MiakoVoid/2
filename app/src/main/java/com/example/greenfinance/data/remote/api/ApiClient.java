package com.example.greenfinance.data.remote.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.greenfinance.common.util.SecurePreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * API客户端配置类
 */
public class ApiClient {
    private static final String BASE_URL = "http://10.0.2.2:8080/api/"; // 模拟器访问本地服务器的地址，必须以/结尾
    private static final String TAG = "ApiClient";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            // 添加日志拦截器，方便调试
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // 创建token拦截器
            Interceptor tokenInterceptor = new Interceptor() {
                @NonNull
                @Override
                public Response intercept(@NonNull Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request.Builder requestBuilder = originalRequest.newBuilder();
                    
                    // 获取token
                    String token = SecurePreferences.getToken();
                    
                    // 为除了注册和登录之外的所有请求添加token
                    String url = originalRequest.url().toString();
                    if (token != null && !url.endsWith("/auth/login") && !url.endsWith("/auth/register")) {
                        Log.d(TAG, "Adding token to request: " + url);
                        requestBuilder.addHeader("Authorization", "Bearer " + token);
                    }
                    
                    Request newRequest = requestBuilder.build();
                    return chain.proceed(newRequest);
                }
            };

            // 创建OkHttpClient
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(tokenInterceptor)
                    .addInterceptor(interceptor)
                    .build();

            // 创建Retrofit实例
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * 获取认证服务接口
     * @return AuthService实例
     */
    public static AuthService getAuthService() {
        return getClient().create(AuthService.class);
    }
}