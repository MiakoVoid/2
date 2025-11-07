package com.example.greenfinance.presentation.home.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.greenfinance.common.util.SecurePreferences;
import com.example.greenfinance.data.model.Bill;
import com.example.greenfinance.databinding.FragmentHomeBinding;
import com.example.greenfinance.presentation.auth.view.LoginActivity;
import com.example.greenfinance.presentation.home.adapter.HomeAdapter;
import com.example.greenfinance.presentation.home.viewmodel.HomeViewModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment implements HomeAdapter.OnHeaderButtonClickListener {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private HomeAdapter homeAdapter;

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

        // 初始化适配器
        initAdapter();

        // 初始化视图
        initViews();

        // 设置监听器
        setupListeners();
    }

    private boolean isUserLoggedIn() {
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
    private void initAdapter() {
        homeAdapter = new HomeAdapter(this);
        binding.rvHomeContent.setAdapter(homeAdapter);
        binding.rvHomeContent.setLayoutManager(new LinearLayoutManager(requireContext()));
        
        // 设置头部数据
        HomeAdapter.HeaderData headerData = new HomeAdapter.HeaderData(
                "¥25.50", 
                "¥125.00", 
                "¥800.00", 
                30, 
                requireContext().getColor(com.example.greenfinance.R.color.budget_sufficient),
                "预算: ¥300.00/¥1000.00"
        );
        homeAdapter.setHeaderData(headerData);

        // 添加示例账单数据
        List<Bill> sampleBills = createSampleBills();
        homeAdapter.setBills(sampleBills);
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
        
        // 添加更多示例数据以测试滚动和分组
        for (int i = 6; i <= 30; i++) {
            LocalDateTime billDate = now.minusDays(i % 7); // 在最近一周内分布
            bills.add(new Bill(i, new BigDecimal(String.valueOf(i * 10 % 100 + 5)), 
                    "餐饮", "餐厅" + i,
                    Date.from(billDate.atZone(ZoneId.systemDefault()).toInstant())));
        }

        return bills;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 清理绑定引用，防止内存泄漏
        binding = null;
    }

    @Override
    public void onManageBudgetClick() {
        // 处理管理预算按钮点击事件
        // 在实际应用中，这里会导航到预算管理页面
    }
}