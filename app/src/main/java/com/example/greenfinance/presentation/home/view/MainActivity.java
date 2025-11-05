package com.example.greenfinance.presentation.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.greenfinance.R;
import com.example.greenfinance.common.util.FabGestureHandler;
import com.example.greenfinance.common.util.SecurePreferences;
import com.example.greenfinance.presentation.auth.view.LoginActivity;
import com.example.greenfinance.presentation.bill.view.TextBillingDialogFragment;
import com.example.greenfinance.presentation.calendar.view.CalendarFragment;
import com.example.greenfinance.presentation.profile.view.ProfileFragment;
import com.example.greenfinance.presentation.report.view.ReportFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements FabGestureHandler.OnFabClickListener {

    private boolean isBatchMode = false;
    private int currentNavigationItemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 检查用户是否已登录，如果未登录则跳转到登录页面
        if (!isUserLoggedIn()) {
            redirectToLogin();
            return;
        }

        // 设置布局
        setContentView(R.layout.activity_main_container);
        
        // 初始化视图
        initViews();
        
        // 默认显示首页Fragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment(), false);
            currentNavigationItemId = R.id.nav_home;
        }
    }

    private boolean isUserLoggedIn() {
        // 初始化安全存储
        SecurePreferences.init(this);
        
        // 检查是否有保存的token且未过期
        return SecurePreferences.getToken() != null && !SecurePreferences.isTokenExpired();
    }

    private void redirectToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            // TODO: 实现搜索功能
            return true;
        } else if (id == R.id.action_batch) {
            // 切换批量处理模式
            toggleBatchMode();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        setupNavigationAndGestures();
        
        // 设置当前选中的导航项
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        if (bottomNavigation != null) {
            bottomNavigation.setSelectedItemId(R.id.nav_home);
        }
        
        // 设置浮动按钮手势检测
        setupFabGestures();
    }

    /**
     * 设置底部导航和手势处理
     */
    protected void setupNavigationAndGestures() {
        // 添加Fragment切换逻辑
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        if (bottomNavigation != null) {
            bottomNavigation.setOnItemSelectedListener(item -> {
                // 避免重复选择相同项
                if (currentNavigationItemId == item.getItemId()) {
                    return true;
                }
                
                Fragment selectedFragment = null;
                int itemId = item.getItemId();
                
                if (itemId == R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                } else if (itemId == R.id.nav_calendar) {
                    selectedFragment = new CalendarFragment();
                } else if (itemId == R.id.nav_report) {
                    selectedFragment = new ReportFragment();
                } else if (itemId == R.id.nav_profile) {
                    selectedFragment = new ProfileFragment();
                }
                
                // 注意：nav_add（添加按钮）不处理，因为它是FAB按钮
                if (selectedFragment != null) {
                    loadFragment(selectedFragment, false);
                    currentNavigationItemId = itemId;
                }
                
                return true;
            });
        }
    }
    
    /**
     * 设置浮动按钮手势检测
     */
    private void setupFabGestures() {
        FloatingActionButton fab = findViewById(R.id.fab_add_bill);
        View fabContainer = findViewById(R.id.fab_container);
        
        // 创建手势处理器
        FabGestureHandler fabGestureHandler = new FabGestureHandler(this);
        
        // 设置触摸监听器
        View.OnTouchListener touchListener = fabGestureHandler;

        // 为FAB和容器设置触摸监听器
        if (fab != null) {
            fab.setOnTouchListener(touchListener);
            fabGestureHandler.setAttachedView(fab);
        }
        
        if (fabContainer != null) {
            fabContainer.setOnTouchListener(touchListener);
        }
    }

    /**
     * 加载Fragment
     * @param fragment 要加载的Fragment
     * @param addToBackStack 是否添加到回退栈
     */
    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        
        // 设置淡入淡出过渡动画，营造内容变化而非页面跳转的视觉感受
        transaction.setCustomAnimations(
                R.anim.fade_in, 
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.fade_out
        );
        
        // 替换Fragment
        transaction.replace(R.id.nav_host_fragment, fragment);
        
        // 添加到回退栈（可选）
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        
        // 提交事务
        transaction.commit();
    }
    
    @Override
    public void onFabClick() {
        // 在任何页面点击都可以打开文字记账弹窗
        TextBillingDialogFragment textBillingDialog = new TextBillingDialogFragment();
        textBillingDialog.show(getSupportFragmentManager(), "text_billing_dialog");
    }

    /**
     * 切换批量处理模式
     */
    private void toggleBatchMode() {
        isBatchMode = !isBatchMode;
        // TODO: 更新UI以反映批量处理模式状态
        // 在批量处理模式下，分类图标应变成方框用来选中
        invalidateOptionsMenu();
    }
    
    /**
     * 根据批量处理模式更新菜单项
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem batchItem = menu.findItem(R.id.action_batch);
        if (isBatchMode) {
            batchItem.setIcon(R.drawable.ic_check_box_outline);
            batchItem.setTitle("完成");
        } else {
            batchItem.setIcon(R.drawable.ic_check_box);
            batchItem.setTitle("批量处理");
        }
        return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}