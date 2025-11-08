package com.example.greenfinance.presentation.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenfinance.R;
import com.example.greenfinance.common.util.BillDateGroupUtil;
import com.example.greenfinance.data.model.Bill;
import com.example.greenfinance.data.model.BillHeader;
import com.example.greenfinance.presentation.bill.adapter.BillAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_BILL_HEADER = 1;
    private static final int TYPE_BILL = 2;

    private final List<Object> items = new ArrayList<>();
    private HeaderData headerData;
    private OnHeaderButtonClickListener headerButtonClickListener;

    public interface OnHeaderButtonClickListener {
        void onManageBudgetClick();
    }

    public HomeAdapter(OnHeaderButtonClickListener listener) {
        this.headerButtonClickListener = listener;
    }

    public void setHeaderData(HeaderData headerData) {
        this.headerData = headerData;
        updateItems();
    }

    public void setBills(List<Bill> bills) {
        // 使用工具类对账单进行分组
        List<Object> groupedItems = BillDateGroupUtil.groupBillsByDate(bills);
        
        items.clear();
        items.add(headerData);
        items.addAll(groupedItems);
        notifyDataSetChanged();
    }

    private void updateItems() {
        if (items.isEmpty()) {
            items.add(headerData);
        } else {
            items.set(0, headerData);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            Object item = items.get(position);
            if (item instanceof BillHeader) {
                return TYPE_BILL_HEADER;
            } else {
                return TYPE_BILL;
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_home_header, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == TYPE_BILL_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_bill_header, parent, false);
            return new BillAdapter.HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_bill, parent, false);
            return new BillAdapter.BillViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = items.get(position);
        
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind(headerData);
        } else if (holder instanceof BillAdapter.HeaderViewHolder) {
            ((BillAdapter.HeaderViewHolder) holder).bind((BillHeader) item);
        } else if (holder instanceof BillAdapter.BillViewHolder) {
            ((BillAdapter.BillViewHolder) holder).bind((Bill) item);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Getter
    public static class HeaderData {
        // Getters
        private String todayExpense;
        private String monthExpense;
        private String monthIncome;
        private int budgetProgress;
        private int budgetProgressColor;
        private String budgetUsageText;

        public HeaderData(String todayExpense, String monthExpense, String monthIncome,
                          int budgetProgress, int budgetProgressColor, String budgetUsageText) {
            this.todayExpense = todayExpense;
            this.monthExpense = monthExpense;
            this.monthIncome = monthIncome;
            this.budgetProgress = budgetProgress;
            this.budgetProgressColor = budgetProgressColor;
            this.budgetUsageText = budgetUsageText;
        }

    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        // 财务概览视图
        private TextView tvTodayExpense;
        private TextView tvMonthExpense;
        private TextView tvMonthIncome;
        
        // 预算视图
        private LinearProgressIndicator budgetProgress;
        private TextView tvBudgetUsage;
        private MaterialButton btnManageBudget;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            
            // 初始化财务概览视图
            tvTodayExpense = itemView.findViewById(R.id.tv_today_expense);
            tvMonthExpense = itemView.findViewById(R.id.tv_month_expense);
            tvMonthIncome = itemView.findViewById(R.id.tv_month_income);
            
            // 初始化预算视图
            budgetProgress = itemView.findViewById(R.id.budget_progress);
            tvBudgetUsage = itemView.findViewById(R.id.tv_budget_usage);
            btnManageBudget = itemView.findViewById(R.id.btn_manage_budget);
            
            // 设置按钮点击监听器
            btnManageBudget.setOnClickListener(v -> {
                if (headerButtonClickListener != null) {
                    headerButtonClickListener.onManageBudgetClick();
                }
            });
        }

        public void bind(HeaderData data) {
            // 绑定财务概览数据
            tvTodayExpense.setText(data.getTodayExpense());
            tvMonthExpense.setText(data.getMonthExpense());
            tvMonthIncome.setText(data.getMonthIncome());
            
            // 绑定预算数据
            budgetProgress.setProgress(data.getBudgetProgress());
            // 注意：在实际应用中，您可能需要根据预算状态选择不同的颜色
            budgetProgress.setIndicatorColor(data.getBudgetProgressColor());
            tvBudgetUsage.setText(data.getBudgetUsageText());
        }
    }
}