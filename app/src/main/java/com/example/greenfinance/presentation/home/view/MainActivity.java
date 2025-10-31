package com.example.greenfinance.presentation.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.lifecycle.ViewModelProvider;

import com.example.greenfinance.R;
import com.example.greenfinance.common.base.BaseActivity;
import com.example.greenfinance.common.util.SecurePreferences;
import com.example.greenfinance.databinding.ActivityMainBinding;
import com.example.greenfinance.data.model.Bill;
import com.example.greenfinance.presentation.auth.view.LoginActivity;
import com.example.greenfinance.presentation.home.adapter.BillAdapter;
import com.example.greenfinance.presentation.home.viewmodel.HomeViewModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private HomeViewModel viewModel;
    private BillAdapter billAdapter;
    private boolean isBatchMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 检查用户是否已登录，如果未登录则跳转到登录页面
        if (!isUserLoggedIn()) {
            redirectToLogin();
            return;
        }

        // 使用DataBinding加载布局
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 初始化ViewModel
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // 将ViewModel绑定到布局
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel); // 启用ViewModel数据绑定

        // 初始化账单适配器
        initBillAdapter();

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
    public boolean onOptionsItemSelected(MenuItem item) {
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
        // 通过DataBinding访问视图组件
    }

    private void initBillAdapter() {
        billAdapter = new BillAdapter();
        binding.rvRecentBills.setAdapter(billAdapter);
        
        // 添加示例数据
        List<Bill> sampleBills = createSampleBills();
        billAdapter.setBills(sampleBills);
    }

    private List<Bill> createSampleBills() {
        List<Bill> bills = new ArrayList<>();
        
        // 创建示例账单数据
        Date now = new Date();
        bills.add(new Bill(1, new BigDecimal("25.50"), "餐饮", "麦当劳", now));
        bills.add(new Bill(2, new BigDecimal("18.00"), "交通", "地铁", now));
        
        // 添加昨天的账单
        Date yesterday = new Date(now.getTime() - 24 * 60 * 60 * 1000);
        bills.add(new Bill(3, new BigDecimal("89.90"), "购物", "超市购物", yesterday));
        bills.add(new Bill(4, new BigDecimal("12.00"), "餐饮", "便利店", yesterday));
        
        // 添加前天的账单
        Date twoDaysAgo = new Date(now.getTime() - 2 * 24 * 60 * 60 * 1000);
        bills.add(new Bill(5, new BigDecimal("35.50"), "餐饮", "肯德基", twoDaysAgo));
        
        return bills;
    }


    private void setupClickListeners() {
        // 使用正确的ID引用管理预算按钮
        binding.btnManageBudget.setOnClickListener(v -> {
            // TODO: 跳转到预算管理页面
        });
    }

    /**
     * 显示添加账单选项菜单
     */
    private void showAddBillOptions() {
        // TODO: 实现三种记账方式的选择菜单
        // 1. 手动记账
        // 2. 截图记账
        // 3. 文本记账
    }

    @Override
    protected void handleAddButtonClick() {
        showAddBillOptions();
    }

    /**
     * 初始化首页数据
     * 包括财务概览、预算信息和账单列表
     */
    private void initHomeData() {
        // 由于已使用DataBinding，此处不再需要手动设置文本
        // 数据将通过ViewModel自动绑定到UI上

        // TODO: 初始化RecyclerView数据
        //初始化账单列表、预算、财务概览等
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
    //批量删除账单
    private void deleteSelectedBills() {
        // TODO: 删除选中的账单
    }
}