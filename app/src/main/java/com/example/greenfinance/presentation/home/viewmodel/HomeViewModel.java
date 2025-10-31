package com.example.greenfinance.presentation.home.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    
    private MutableLiveData<String> todayExpense = new MutableLiveData<>();
    private MutableLiveData<String> monthExpense = new MutableLiveData<>();
    private MutableLiveData<String> monthIncome = new MutableLiveData<>();
    private MutableLiveData<String> remainingBudget = new MutableLiveData<>();
    private MutableLiveData<Integer> budgetProgress = new MutableLiveData<>();
    
    public HomeViewModel() {
        // 初始化数据
        initData();
    }
    
    private void initData() {
        // TODO: 从数据仓库获取真实数据
        // 示例数据初始化
        todayExpense.setValue("¥128.50");
        monthExpense.setValue("¥2,856.20");
        monthIncome.setValue("¥8,500.00");
        remainingBudget.setValue("¥2,143.80");
        budgetProgress.setValue(57); // 57%进度
    }
    
    public LiveData<String> getTodayExpense() { return todayExpense; }
    public LiveData<String> getMonthExpense() { return monthExpense; }
    public LiveData<String> getMonthIncome() { return monthIncome; }
    public LiveData<String> getRemainingBudget() { return remainingBudget; }
    public LiveData<Integer> getBudgetProgress() { return budgetProgress; }
    
    // TODO: 添加业务逻辑方法
}