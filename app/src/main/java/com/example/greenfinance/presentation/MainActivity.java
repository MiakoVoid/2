package com.example.greenfinance.presentation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.greenfinance.R;
import com.example.greenfinance.common.util.FabGestureHandler;
import com.example.greenfinance.common.util.SecurePreferences;
import com.example.greenfinance.presentation.auth.view.LoginActivity;
import com.example.greenfinance.presentation.bill.view.ManualBillingActivity;
import com.example.greenfinance.presentation.bill.view.ScreenshotBillingActivity;
import com.example.greenfinance.presentation.bill.view.TextBillingDialogFragment;
import com.example.greenfinance.presentation.calendar.view.CalendarFragment;
import com.example.greenfinance.presentation.home.view.HomeFragment;
import com.example.greenfinance.presentation.profile.view.ProfileFragment;
import com.example.greenfinance.presentation.report.view.ReportFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements FabGestureHandler.OnFabClickListener {
    private static final String TAG = "MainActivity";
    
    private int currentNavigationItemId = -1;
    
    // 用于缓存Fragment实例
    private final Map<Integer, Fragment> fragmentCache = new HashMap<>();
    
    // FAB选项菜单相关
    private ViewGroup fabOptionsMenu;
    private boolean isFabMenuOpen = false;

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

        // 默认显示首页Fragment，只在首次创建时加载
        if (savedInstanceState == null) {
            // 检查是否已经有Fragment在nav_host_fragment中
            Fragment existingFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            if (existingFragment == null) {
                loadFragment(R.id.nav_home);
            } else {
                // 如果已经有Fragment存在，确保currentNavigationItemId被正确设置
                // 这可能发生在配置更改（如屏幕旋转）后
                currentNavigationItemId = R.id.nav_home;
            }
        } else {
            // 在配置更改后恢复currentNavigationItemId
            // 可以通过savedInstanceState恢复，或者通过检查当前显示的Fragment
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            if (currentFragment != null) {
                // 根据当前Fragment设置currentNavigationItemId
                String tag = currentFragment.getTag();
                if (tag != null) {
                    try {
                        currentNavigationItemId = Integer.parseInt(tag);
                    } catch (NumberFormatException e) {
                        // 如果无法解析tag，则使用默认值
                        currentNavigationItemId = R.id.nav_home;
                    }
                }
            }
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
    
    private void initViews() {
        try {
            setupNavigationAndGestures();

            // 设置当前选中的导航项
            BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
            if (bottomNavigation != null) {
                // 只在当前没有选中项时设置默认选中项
                if (bottomNavigation.getSelectedItemId() == 0) {
                    bottomNavigation.setSelectedItemId(R.id.nav_home);
                }
            }

            // 设置浮动按钮手势检测
            setupFabGestures();
            
            // 初始化FAB选项菜单
            initFabOptionsMenu();
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
                // 先移除已有的监听器，避免重复设置
                bottomNavigation.setOnItemSelectedListener(null);
                bottomNavigation.setOnItemReselectedListener(null);
                
                bottomNavigation.setOnItemSelectedListener(item -> {
                    try {
                        int itemId = item.getItemId();
                        
                        // 特殊处理添加按钮
                        if (itemId == R.id.nav_add) {
                            onFabClick();
                            // 不改变当前选中项
                            if (currentNavigationItemId != -1) {
                                bottomNavigation.setSelectedItemId(currentNavigationItemId);
                            }
                            return true;
                        }
                        
                        // 避免重复选择相同项
                        if (currentNavigationItemId == itemId) {
                            return true;
                        }

                        loadFragment(itemId);
                        currentNavigationItemId = itemId;

                        return true;
                    } catch (Exception e) {
                        Log.e(TAG, "Error handling navigation item selection", e);
                        return false;
                    }
                });
                
                // 防止重复选择的另一种方式：禁用重新选择
                bottomNavigation.setOnItemReselectedListener(item -> {
                    // 特殊处理添加按钮
                    if (item.getItemId() == R.id.nav_add) {
                        onFabClick();
                    }
                    // 当用户重复选择同一个项时，不做任何操作
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

            // 将手势监听器附加到FAB上，而不是容器，确保滑动起点必须是FAB
            if (fab != null) {
                fab.setOnTouchListener(fabGestureHandler);
            }
            
            // 同时给FAB设置点击监听器，处理点击事件
            if (fab != null) {
                fab.setOnClickListener(v -> onFabClick());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error setting up FAB gestures", e);
        }
    }

    /**
     * 初始化FAB选项菜单
     */
    private void initFabOptionsMenu() {
        try {
            // 动态加载FAB选项菜单布局
            androidx.coordinatorlayout.widget.CoordinatorLayout rootLayout = findViewById(R.id.coordinator_layout);
            if (rootLayout != null) {
                fabOptionsMenu = (ViewGroup) getLayoutInflater().inflate(R.layout.fab_options_menu, rootLayout, false);
                fabOptionsMenu.setVisibility(View.GONE);
                fabOptionsMenu.setAlpha(0f);
                
                // 添加到CoordinatorLayout中
                androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams params = 
                    new androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams(
                        androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams.WRAP_CONTENT,
                        androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = android.view.Gravity.BOTTOM | android.view.Gravity.CENTER_HORIZONTAL;
                params.bottomMargin = getResources().getDimensionPixelSize(R.dimen.fab_options_bottom_margin);
                fabOptionsMenu.setLayoutParams(params);
                rootLayout.addView(fabOptionsMenu);
                
                // 设置各选项的点击事件
                setupFabOptionClickListeners();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error initializing FAB options menu", e);
        }
    }

    /**
     * 设置FAB选项的点击监听器
     */
    private void setupFabOptionClickListeners() {
        if (fabOptionsMenu == null) {
            return;
        }
        
        View manualBillingCard = fabOptionsMenu.findViewById(R.id.card_manual_billing);
        View screenshotBillingCard = fabOptionsMenu.findViewById(R.id.card_screenshot_billing);
        View textBillingCard = fabOptionsMenu.findViewById(R.id.card_text_billing);
        
        if (manualBillingCard != null) {
            manualBillingCard.setOnClickListener(v -> {
                closeFabOptionsMenu();
                navigateToManualBilling();
            });
        }
        
        if (screenshotBillingCard != null) {
            screenshotBillingCard.setOnClickListener(v -> {
                closeFabOptionsMenu();
                navigateToScreenshotBilling();
            });
        }
        
        if (textBillingCard != null) {
            textBillingCard.setOnClickListener(v -> {
                closeFabOptionsMenu();
                openTextBillingDialog();
            });
        }
    }

    /**
     * 显示FAB选项菜单
     */
    private void showFabOptionsMenu() {
        if (fabOptionsMenu != null && !isFabMenuOpen) {
            isFabMenuOpen = true;
            fabOptionsMenu.setVisibility(View.VISIBLE);
            fabOptionsMenu.setAlpha(0f);
            fabOptionsMenu.animate()
                    .alpha(1f)
                    .setDuration(200)
                    .setListener(null)
                    .start();
        }
    }

    /**
     * 隐藏FAB选项菜单
     */
    private void closeFabOptionsMenu() {
        if (fabOptionsMenu != null && isFabMenuOpen) {
            isFabMenuOpen = false;
            fabOptionsMenu.animate()
                    .alpha(0f)
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (fabOptionsMenu != null) {
                                fabOptionsMenu.setVisibility(View.GONE);
                            }
                        }
                    })
                    .start();
        }
    }

    /**
     * 加载Fragment
     *
     * @param fragmentId 要加载的Fragment ID
     */
    private void loadFragment(int fragmentId) {
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // 设置淡入淡出过渡动画，营造内容变化而非页面跳转的视觉感受
            transaction.setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
            );

            // 隐藏当前显示的Fragment
            if (currentNavigationItemId != -1) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(currentNavigationItemId));
                if (currentFragment != null && currentFragment.isAdded() && currentFragment.isVisible()) {
                    transaction.hide(currentFragment);
                }
            }

            // 获取或创建目标Fragment
            Fragment targetFragment = fragmentCache.get(fragmentId);
            if (targetFragment == null) {
                // 创建新的Fragment实例
                targetFragment = createFragmentById(fragmentId);
                if (targetFragment != null) {
                    fragmentCache.put(fragmentId, targetFragment);
                    transaction.add(R.id.nav_host_fragment, targetFragment, String.valueOf(fragmentId));
                }
            } else {
                // 显示已缓存的Fragment
                if (targetFragment.isAdded()) {
                    // 如果Fragment已添加，则显示它
                    transaction.show(targetFragment);
                } else {
                    // 如果Fragment未添加，则添加它
                    transaction.add(R.id.nav_host_fragment, targetFragment, String.valueOf(fragmentId));
                }
            }

            // 提交事务
            transaction.commit();
        } catch (Exception e) {
            Log.e(TAG, "Error loading fragment", e);
        }
    }

    /**
     * 根据ID创建Fragment实例
     * @param id Fragment ID
     * @return 对应的Fragment实例
     */
    private Fragment createFragmentById(int id) {
        if (id == R.id.nav_home) {
            return new HomeFragment();
        } else if (id == R.id.nav_calendar) {
            return new CalendarFragment();
        } else if (id == R.id.nav_report) {
            return new ReportFragment();
        } else if (id == R.id.nav_profile) {
            return new ProfileFragment();
        } else {
            return null;
        }
    }

    /**
     * 处理FAB点击事件，显示记账方式选项菜单
     */
    @Override
    public void onFabClick() {
        try {
            // 显示FAB选项菜单而不是对话框
            showFabOptionsMenu();
        } catch (Exception e) {
            Log.e(TAG, "Error handling FAB click", e);
        }
    }
    
    /**
     * 处理FAB左滑手势，进入截图记账页面
     */
    public void onFabSwipeLeft() {
        try {
            // 关闭FAB选项菜单（如果打开）
            closeFabOptionsMenu();
            // 直接进入截图记账页面
            navigateToScreenshotBilling();
        } catch (Exception e) {
            Log.e(TAG, "Error handling FAB swipe left", e);
        }
    }
    
    /**
     * 处理FAB右滑手势，进入手动记账页面
     */
    public void onFabSwipeRight() {
        try {
            // 关闭FAB选项菜单（如果打开）
            closeFabOptionsMenu();
            // 直接进入手动记账页面
            navigateToManualBilling();
        } catch (Exception e) {
            Log.e(TAG, "Error handling FAB swipe right", e);
        }
    }
    
    /**
     * 处理FAB上滑手势，进入文字记账页面
     */
    public void onFabSwipeUp() {
        try {
            // 关闭FAB选项菜单（如果打开）
            closeFabOptionsMenu();
            // 直接打开文字记账弹窗
            openTextBillingDialog();
        } catch (Exception e) {
            Log.e(TAG, "Error handling FAB swipe up", e);
        }
    }
    
    /**
     * 处理FAB下滑手势（暂不处理）
     */
    public void onFabSwipeDown() {
        // 暂不处理下滑手势
        Log.d(TAG, "FAB swipe down - no action");
    }

    /**
     * 打开文字记账对话框
     */
    private void openTextBillingDialog() {
        TextBillingDialogFragment textBillingDialog = new TextBillingDialogFragment();
        textBillingDialog.show(getSupportFragmentManager(), "text_billing_dialog");
    }
    
    /**
     * 跳转到手动记账页面
     */
    private void navigateToManualBilling() {
        Intent intent = new Intent(this, ManualBillingActivity.class);
        startActivity(intent);
    }
    
    /**
     * 跳转到截图记账页面
     */
    private void navigateToScreenshotBilling() {
        Intent intent = new Intent(this, ScreenshotBillingActivity.class);
        startActivity(intent);
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 如果FAB菜单是打开的，且触摸事件发生在菜单外部，则关闭菜单
        if (isFabMenuOpen && fabOptionsMenu != null) {
            Rect rect = new Rect();
            fabOptionsMenu.getGlobalVisibleRect(rect);
            if (!rect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                closeFabOptionsMenu();
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
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