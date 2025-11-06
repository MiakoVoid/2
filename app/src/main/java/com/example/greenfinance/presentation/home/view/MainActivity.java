package com.example.greenfinance.presentation.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private static final String TAG = "MainActivity";

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
        try {
            // 初始化安全存储
            SecurePreferences.init(this);

            // 检查是否有保存的token且未过期
            String token = SecurePreferences.getToken();
            boolean isTokenExpired = SecurePreferences.isTokenExpired();

            Log.d(TAG, "Token: " + token + ", Expired: " + isTokenExpired);

            return token != null && !isTokenExpired;
        } catch (Exception e) {
            Log.e(TAG, "Error checking user login status", e);
            return false;
        }
    }

    private void redirectToLogin() {
        try {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Log.e(TAG, "Error redirecting to login", e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.home_menu, menu);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error creating options menu", e);
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        try {
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
        } catch (Exception e) {
            Log.e(TAG, "Error handling menu item selection", e);
            return false;
        }
    }

    private void initViews() {
        try {
            setupNavigationAndGestures();

            // 设置当前选中的导航项
            BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
            if (bottomNavigation != null) {
                bottomNavigation.setSelectedItemId(R.id.nav_home);
            }

            // 设置浮动按钮手势检测
            setupFabGestures();
        } catch (Exception e) {
            Log.e(TAG, "Error initializing views", e);
        }
    }

    /**
     * 设置底部导航和手势处理
     */
    protected void setupNavigationAndGestures() {
        try {
            // 添加Fragment切换逻辑
            BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
            if (bottomNavigation != null) {
                bottomNavigation.setOnItemSelectedListener(item -> {
                    try {
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
                    } catch (Exception e) {
                        Log.e(TAG, "Error handling navigation item selection", e);
                        return false;
                    }
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "Error setting up navigation and gestures", e);
        }
    }

    /**
     * 设置浮动按钮手势检测
     * 修复：避免直接给FloatingActionButton设置OnTouchListener，因为这需要实现performClick方法
     * 改为只给FAB的容器设置手势监听器，而FAB本身的点击事件独立处理
     */
    private void setupFabGestures() {
        try {
            FloatingActionButton fab = findViewById(R.id.fab_add_bill);
            View fabContainer = findViewById(R.id.fab_container);

            // 创建手势处理器
            FabGestureHandler fabGestureHandler = new FabGestureHandler(this);

            // 不再直接给FAB设置触摸监听器，避免违反无障碍性要求

            // 只将手势监听器附加到容器上
            if (fabContainer != null) {
                fabContainer.setOnTouchListener(fabGestureHandler);
            }

            // 使用setOnClickListener处理FAB的点击事件，满足无障碍性要求
            if (fab != null) {
                fab.setOnClickListener(v -> onFabClick());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error setting up FAB gestures", e);
        }
    }

    /**
     * 加载Fragment
     * @param fragment 要加载的Fragment
     * @param addToBackStack 是否添加到回退栈
     */
    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        try {
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
        } catch (Exception e) {
            Log.e(TAG, "Error loading fragment", e);
        }
    }

    @Override
    public void onFabClick() {
        try {
            // 在任何页面点击都可以打开文字记账弹窗
            TextBillingDialogFragment textBillingDialog = new TextBillingDialogFragment();
            textBillingDialog.show(getSupportFragmentManager(), "text_billing_dialog");
        } catch (Exception e) {
            Log.e(TAG, "Error handling FAB click", e);
        }
    }

    /**
     * 切换批量处理模式
     */
    private void toggleBatchMode() {
        try {
            isBatchMode = !isBatchMode;
            // TODO: 更新UI以反映批量处理模式状态
            // 在批量处理模式下，分类图标应变成方框用来选中
            invalidateOptionsMenu();
        } catch (Exception e) {
            Log.e(TAG, "Error toggling batch mode", e);
        }
    }

    /**
     * 根据批量处理模式更新菜单项
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        try {
            MenuItem batchItem = menu.findItem(R.id.action_batch);
            if (batchItem != null) {
                if (isBatchMode) {
                    batchItem.setIcon(R.drawable.ic_check_box_outline);
                    batchItem.setTitle("完成");
                } else {
                    batchItem.setIcon(R.drawable.ic_check_box);
                    batchItem.setTitle("批量处理");
                }
            }
            return super.onPrepareOptionsMenu(menu);
        } catch (Exception e) {
            Log.e(TAG, "Error preparing options menu", e);
            return super.onPrepareOptionsMenu(menu);
        }
    }

    @Override
    public void finish() {
        try {
            super.finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } catch (Exception e) {
            Log.e(TAG, "Error finishing activity", e);
            super.finish();
        }
    }
}
