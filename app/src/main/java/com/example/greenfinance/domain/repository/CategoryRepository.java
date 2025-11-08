package com.example.greenfinance.domain.repository;

import androidx.lifecycle.LiveData;

import com.example.greenfinance.data.model.Category;
import com.example.greenfinance.data.remote.response.BaseResponse;

import java.util.List;

public interface CategoryRepository {
    /**
     * 获取分类列表
     * @param type 1=支出，2=收入
     * @return 分类列表
     */
    LiveData<BaseResponse<List<Category>>> getCategories(int type);
}