package com.example.greenfinance.presentation.home.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.greenfinance.R;

public class HomeViewModel extends ViewModel {
    
    private MutableLiveData<String> todayExpense = new MutableLiveData<>();
    private MutableLiveData<String> monthExpense = new MutableLiveData<>();
    private MutableLiveData<String> monthIncome = new MutableLiveData<>();
    private MutableLiveData<String> remainingBudget = new MutableLiveData<>();
    private MutableLiveData<Integer> budgetProgress = new MutableLiveData<>();
    private MutableLiveData<Integer> budgetProgressColor = new MutableLiveData<>();
    private MutableLiveData<String> budgetUsageText = new MutableLiveData<>();
    
    // 添加总预算字段
    private String totalBudget = "¥5,000.00";
    private String usedBudget = "¥2,856.20";
    
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
        budgetProgressColor.setValue(R.color.budget_sufficient);
        // 修改为"实际使用/总预算"格式
        budgetUsageText.setValue(usedBudget + "/" + totalBudget);
    }
    
    public LiveData<String> getTodayExpense() { return todayExpense; }
    public LiveData<String> getMonthExpense() { return monthExpense; }
    public LiveData<String> getMonthIncome() { return monthIncome; }
    public LiveData<String> getRemainingBudget() { return remainingBudget; }
    public LiveData<Integer> getBudgetProgress() { return budgetProgress; }
    public int getBudgetProgressColor() { return budgetProgressColor.getValue() != null ? budgetProgressColor.getValue() : R.color.budget_sufficient; }
    public LiveData<String> getBudgetUsageText() { return budgetUsageText; }
    
    // TODO: 添加业务逻辑方法
}