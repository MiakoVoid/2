package com.example.greenfinance.common.base;

import android.content.Intent;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.greenfinance.R;
import com.example.greenfinance.presentation.home.view.MainActivity;
import com.example.greenfinance.presentation.calendar.view.CalendarActivity;
import com.example.greenfinance.presentation.report.view.ReportActivity;
import com.example.greenfinance.presentation.profile.view.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * 基础Activity类，提供通用的底部导航功能
 */
public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {

    protected void setupBottomNavigation(BottomNavigationView bottomNavigation) {
        bottomNavigation.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Class<?> targetActivity = null;

        if (itemId == R.id.nav_home) {
            targetActivity = MainActivity.class;
        } else if (itemId == R.id.nav_calendar) {
            targetActivity = CalendarActivity.class;
        } else if (itemId == R.id.nav_report) {
            targetActivity = ReportActivity.class;
        } else if (itemId == R.id.nav_profile) {
            targetActivity = ProfileActivity.class;
        } else if (itemId == R.id.nav_add) {
            // 中间添加按钮的处理应在各Activity中单独实现
            handleAddButtonClick();
            return true;
        }

        // 如果点击的是当前页面，则不跳转
        if (targetActivity != null && !this.getClass().equals(targetActivity)) {
            Intent intent = new Intent(this, targetActivity);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        return true;
    }

    /**
     * 处理添加按钮点击事件，子类需要重写此方法
     */
    protected abstract void handleAddButtonClick();
}
