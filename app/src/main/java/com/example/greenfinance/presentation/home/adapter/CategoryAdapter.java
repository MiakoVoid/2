package com.example.greenfinance.presentation.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenfinance.R;

import java.util.List;

import lombok.Data;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    
    @Data
    public static class CategoryItem {
        private long id;
        private String name;
        private int iconResId;
        
        public CategoryItem(long id, String name, int iconResId) {
            this.id = id;
            this.name = name;
            this.iconResId = iconResId;
        }

    }
    
    public interface OnCategoryClickListener {
        void onCategoryClick(long categoryId, String categoryName);
    }
    
    private Context context;
    private List<CategoryItem> categories;
    private OnCategoryClickListener listener;
    private long selectedCategoryId = -1;
    
    public CategoryAdapter(Context context, List<CategoryItem> categories) {
        this.context = context;
        this.categories = categories;
    }
    
    public void setOnCategoryClickListener(OnCategoryClickListener listener) {
        this.listener = listener;
    }
    
    public void setSelectedCategory(long categoryId) {
        this.selectedCategoryId = categoryId;
        notifyDataSetChanged();
    }
    
    public void updateCategories(List<CategoryItem> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryItem category = categories.get(position);
        holder.bind(category);
    }
    
    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }
    
    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivCategoryIcon;
        private TextView tvCategoryName;
        
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCategoryIcon = itemView.findViewById(R.id.iv_category_icon);
            tvCategoryName = itemView.findViewById(R.id.tv_category_name);
        }
        
        public void bind(CategoryItem category) {
            ivCategoryIcon.setImageResource(category.getIconResId());
            tvCategoryName.setText(category.getName());
            
            // 设置选中状态
            if (category.getId() == selectedCategoryId) {
                itemView.setBackgroundColor(context.getResources().getColor(R.color.selected_category_background));
            } else {
                itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            }
            
            // 设置点击事件
            itemView.setOnClickListener(v -> {
                selectedCategoryId = category.getId();
                notifyDataSetChanged();
                
                if (listener != null) {
                    listener.onCategoryClick(category.getId(), category.getName());
                }
            });
        }
    }
}