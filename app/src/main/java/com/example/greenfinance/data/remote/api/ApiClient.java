package com.example.greenfinance.data.remote.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * API客户端配置类
 */
public class ApiClient {
    private static final String BASE_URL = "http://10.0.2.2:8080/api/"; // 模拟器访问本地服务器的地址，必须以/结尾
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            // 添加日志拦截器，方便调试
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // 创建OkHttpClient
            OkHttpClient client = new OkHttpClient.Builder()
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