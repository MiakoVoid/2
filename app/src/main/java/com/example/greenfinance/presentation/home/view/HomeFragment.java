package com.example.greenfinance.presentation.home.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.greenfinance.R;
import com.example.greenfinance.common.util.SecurePreferences;
import com.example.greenfinance.data.model.Bill;
import com.example.greenfinance.databinding.FragmentHomeBinding;
import com.example.greenfinance.presentation.auth.view.LoginActivity;
import com.example.greenfinance.presentation.home.adapter.BillAdapter;
import com.example.greenfinance.presentation.home.viewmodel.HomeViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private boolean isBatchMode = false;
    private HomeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 检查用户是否已登录，如果未登录则跳转到登录页面
        if (!isUserLoggedIn()) {
            redirectToLogin();
            // 返回一个空视图，因为我们将跳转到登录页面
            return new View(getContext());
        }

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 如果用户未登录，则不执行后续操作
        if (!isUserLoggedIn()) {
            return;
        }

        // 初始化ViewModel
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // 将ViewModel绑定到布局
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewModel(viewModel); // 启用ViewModel数据绑定

        // 初始化账单适配器
        initBillAdapter();

        // 初始化视图
        initViews();
    }

    private boolean isUserLoggedIn() {
        // 初始化安全存储
        if (requireContext() != null) {
            SecurePreferences.init(requireContext());
        }
        
        // 检查是否有保存的token且未过期
        return SecurePreferences.getToken() != null && !SecurePreferences.isTokenExpired();
    }

    private void redirectToLogin() {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void initViews() {
        // 不再设置底部导航，因为底部导航在容器Activity中
    }

    private void initBillAdapter() {
        BillAdapter billAdapter = new BillAdapter();
        binding.rvRecentBills.setAdapter(billAdapter);
        // 设置LayoutManager
        binding.rvRecentBills.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(requireContext()));

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
        if (getActivity() != null) {
            getActivity().invalidateOptionsMenu();
        }
    }

    /**
     * 根据批量处理模式更新菜单项
     */
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
    }

    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem batchItem = menu.findItem(R.id.action_batch);
        if (isBatchMode) {
            batchItem.setIcon(R.drawable.ic_check_box_outline);
            batchItem.setTitle("完成");
        } else {
            batchItem.setIcon(R.drawable.ic_check_box);
            batchItem.setTitle("批量处理");
        }
        super.onPrepareOptionsMenu(menu);
    }

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

    /**
     * 显示文本记账对话框
     */
    public void showTextBillingDialog() {
        // 确保Fragment仍然附加到Activity
        if (getActivity() == null || !isAdded()) {
            return;
        }
        
        // 创建并显示文本记账对话框
        Dialog dialog = new Dialog(requireContext());
        
        // 设置对话框无标题样式以更好地适配自定义布局
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_text_billing);

        // 设置对话框中的控件事件
        TextInputEditText etTextInput = dialog.findViewById(R.id.et_text_input);

        dialog.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            // 处理文本记账逻辑
            if (etTextInput != null && etTextInput.getText() != null) {
                String inputText = etTextInput.getText().toString();
                // TODO: 处理输入的文本并保存账单
            }
            dialog.dismiss();
        });

        dialog.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());

        dialog.findViewById(R.id.iv_calendar).setOnClickListener(v -> {
            // TODO: 显示日期选择器
        });

        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 清理绑定引用，防止内存泄漏
        if (binding != null) {
            binding = null;
        }
    }
}