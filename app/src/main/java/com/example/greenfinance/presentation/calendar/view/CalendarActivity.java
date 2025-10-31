package com.example.greenfinance.presentation.calendar.view;

import android.os.Bundle;
import com.example.greenfinance.R;
import com.example.greenfinance.common.base.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CalendarActivity extends BaseActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        setupBottomNavigation(bottomNavigation);

        // 设置当前选中项
        bottomNavigation.setSelectedItemId(R.id.nav_calendar);

        // TODO: 初始化日历页面内容
    }

    @Override
    protected void handleAddButtonClick() {
        // TODO: 实现添加账单功能
    }
}
