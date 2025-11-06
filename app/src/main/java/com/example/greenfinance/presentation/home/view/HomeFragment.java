package com.example.greenfinance.presentation.home.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.greenfinance.R;
import com.example.greenfinance.common.util.SecurePreferences;
import com.example.greenfinance.data.model.Bill;
import com.example.greenfinance.databinding.FragmentHomeBinding;
import com.example.greenfinance.presentation.auth.view.LoginActivity;
import com.example.greenfinance.presentation.home.adapter.BillAdapter;
import com.example.greenfinance.presentation.home.viewmodel.HomeViewModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private boolean isBatchMode = false;
    private HomeViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 检查用户是否已登录，如果未登录则跳转到登录页面
        if (isUserLoggedIn()) {
            redirectToLogin();
            // 返回一个空视图，因为我们将跳转到登录页面
            return new View(requireContext());
        }

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 如果用户未登录，则不执行后续操作
        if (isUserLoggedIn()) {
            return;
        }

        // 初始化ViewModel
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // 将ViewModel绑定到布局
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewModel(viewModel);

        // 初始化账单适配器
        initBillAdapter();

        // 初始化视图
        initViews();

        // 设置监听器
        setupListeners();

        // 注册 MenuProvider
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu);
                setupMenuClickListeners(menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                // 如果需要处理菜单项选择事件，可以在这里实现
                return false;
            }

            @Override
            public void onPrepareMenu(@NonNull Menu menu) {
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
            }
        }, getViewLifecycleOwner());
    }

    private boolean isUserLoggedIn() {
        // 检查是否有保存的token且未过期
        return SecurePreferences.getToken() == null || SecurePreferences.isTokenExpired();
    }

    private void redirectToLogin() {
        // 使用requireActivity()替代getActivity()
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    private void initViews() {
        // 初始化视图组件
        // 例如：设置刷新布局、空状态视图等
    }

    private void setupListeners() {
        // 设置各种点击监听器
        // 例如：浮动操作按钮、筛选按钮等
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initBillAdapter() {
        BillAdapter billAdapter = new BillAdapter();
        binding.rvRecentBills.setAdapter(billAdapter);
        // 设置LayoutManager
        binding.rvRecentBills.setLayoutManager(new LinearLayoutManager(requireContext()));

        // 添加示例数据
        List<Bill> sampleBills = createSampleBills();
        billAdapter.setBills(sampleBills);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<Bill> createSampleBills() {
        List<Bill> bills = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();

        // 创建示例账单数据
        bills.add(new Bill(1, new BigDecimal("25.50"), "餐饮", "麦当劳",
                Date.from(now.atZone(ZoneId.systemDefault()).toInstant())));
        bills.add(new Bill(2, new BigDecimal("18.00"), "交通", "地铁",
                Date.from(now.atZone(ZoneId.systemDefault()).toInstant())));

        // 添加昨天的账单
        LocalDateTime yesterday = now.minusDays(1);
        bills.add(new Bill(3, new BigDecimal("89.90"), "购物", "超市购物",
                Date.from(yesterday.atZone(ZoneId.systemDefault()).toInstant())));
        bills.add(new Bill(4, new BigDecimal("12.00"), "餐饮", "便利店",
                Date.from(yesterday.atZone(ZoneId.systemDefault()).toInstant())));

        // 添加前天的账单
        LocalDateTime twoDaysAgo = now.minusDays(2);
        bills.add(new Bill(5, new BigDecimal("35.50"), "餐饮", "肯德基",
                Date.from(twoDaysAgo.atZone(ZoneId.systemDefault()).toInstant())));

        return bills;
    }

    /**
     * 切换批量处理模式
     */
    private void toggleBatchMode() {
        isBatchMode = !isBatchMode;
        // 更新UI以反映批量处理模式状态
        if (isAdded()) {
            requireActivity().invalidateOptionsMenu();
        }
    }

    /**
     * 为菜单项设置点击监听器
     */
    private void setupMenuClickListeners(Menu menu) {
        // 搜索菜单项
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            searchItem.setOnMenuItemClickListener(item -> {
                // TODO: 实现搜索功能
                // 例如：显示搜索对话框或跳转到搜索页面
                showSearchDialog();
                return true;
            });
        }

        // 批量处理菜单项
        MenuItem batchItem = menu.findItem(R.id.action_batch);
        if (batchItem != null) {
            batchItem.setOnMenuItemClickListener(item -> {
                // 切换批量处理模式
                toggleBatchMode();
                return true;
            });
        }

        // 添加其他菜单项的监听器...
    }

    /**
     * 显示搜索对话框
     */
    private void showSearchDialog() {
        // TODO: 实现搜索对话框
        // 例如：显示一个包含搜索框的对话框
        // 或者启动一个搜索Activity
    }

    /**
     * 处理批量操作完成
     */
    private void completeBatchOperation() {
        // TODO: 实现批量操作完成逻辑
        isBatchMode = false;
        if (isAdded()) {
            requireActivity().invalidateOptionsMenu();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 清理绑定引用，防止内存泄漏
        binding = null;
    }
}
