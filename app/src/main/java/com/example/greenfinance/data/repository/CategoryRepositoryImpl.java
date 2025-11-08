package com.example.greenfinance.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.greenfinance.data.model.Category;
import com.example.greenfinance.data.remote.api.ApiClient;
import com.example.greenfinance.data.remote.api.CategoryService;
import com.example.greenfinance.data.remote.response.BaseResponse;
import com.example.greenfinance.domain.repository.CategoryRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepositoryImpl implements CategoryRepository {
    private static final String TAG = "CategoryRepositoryImpl";
    private static CategoryRepositoryImpl instance;
    private CategoryService categoryService;

    private CategoryRepositoryImpl() {
        categoryService = ApiClient.getCategoryService();
    }

    public static synchronized CategoryRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new CategoryRepositoryImpl();
        }
        return instance;
    }

    @Override
    public LiveData<BaseResponse<List<Category>>> getCategories(int type) {
        MutableLiveData<BaseResponse<List<Category>>> liveData = new MutableLiveData<>();
        
        categoryService.getCategories(type).enqueue(new Callback<BaseResponse<List<Category>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Category>>> call, Response<BaseResponse<List<Category>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.setValue(response.body());
                } else {
                    Log.e(TAG, "获取分类列表失败: " + response.message());
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Category>>> call, Throwable t) {
                Log.e(TAG, "获取分类列表网络错误: " + t.getMessage(), t);
                liveData.setValue(null);
            }
        });
        
        return liveData;
    }
}