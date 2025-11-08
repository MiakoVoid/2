package com.example.greenfinance.data.remote.api;

import com.example.greenfinance.data.model.Category;
import com.example.greenfinance.data.remote.response.BaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CategoryService {
    /**
     * 获取所有分类（含子分类）
     * @param type 1=支出，2=收入
     * @return 分类列表
     */
    @GET("categories")
    Call<BaseResponse<List<Category>>> getCategories(@Query("type") int type);
}