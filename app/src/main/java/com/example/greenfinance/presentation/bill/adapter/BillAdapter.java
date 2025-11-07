package com.example.greenfinance.presentation.bill.adapter;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BillAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<Object> items = new ArrayList<>();
    
    // 构造函数
    public BillAdapter() {
    }

    // 设置数据并按日期分组
    public void setBills(List<Bill> bills) {
        items.clear();
        
        // 使用工具类进行分组
        if (bills != null) {
            items.addAll(BillDateGroupUtil.groupBillsByDate(bills));
        }
        
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof BillHeader) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_bill_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_bill, parent, false);
            return new BillViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            BillHeader header = (BillHeader) items.get(position);
            ((HeaderViewHolder) holder).bind(header);
        } else if (holder instanceof BillViewHolder) {
            Bill bill = (Bill) items.get(position);
            ((BillViewHolder) holder).bind(bill);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Header ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHeaderDate;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeaderDate = itemView.findViewById(R.id.tv_header_date);
        }

        public void bind(BillHeader header) {
            tvHeaderDate.setText(header.getDateText());
        }
    }

    // Bill Item ViewHolder
    public static class BillViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMerchant;
        private TextView tvAmount;
        private TextView tvCategory;
        private TextView tvTime;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMerchant = itemView.findViewById(R.id.tv_merchant);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvCategory = itemView.findViewById(R.id.tv_category);
            tvTime = itemView.findViewById(R.id.tv_time);
        }

        public void bind(Bill bill) {
            tvMerchant.setText(bill.getMerchant());
            tvAmount.setText("¥" + bill.getAmount().toString());
            tvCategory.setText(bill.getCategoryName());
            
            // 格式化时间显示
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            tvTime.setText(timeFormat.format(bill.getBillTime()));
        }
    }
}