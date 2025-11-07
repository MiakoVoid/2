package com.example.greenfinance.presentation.calendar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenfinance.R;
import com.example.greenfinance.data.model.Category;

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
    
    public long getSelectedCategoryId() {
        return selectedCategoryId;
    }
    
    public void updateCategories(List<CategoryItem> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }
    
    /**
     * 从Category模型列表创建CategoryItem列表
     * @param categoryList Category模型列表
     * @return CategoryItem列表
     */
    public static List<CategoryItem> createCategoryItems(List<Category> categoryList) {
        // 这里应该根据iconIdentifier加载对应的资源ID
        // 为了简化，我们使用固定的映射
        // 实际项目中应该根据iconIdentifier动态加载图标资源
        return categoryList.stream().map(category -> {
            int iconResId = getIconResourceId(category.getIconIdentifier());
            return new CategoryItem(category.getId(), category.getName(), iconResId);
        }).collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * 根据图标标识符获取资源ID
     * @param iconIdentifier 图标标识符
     * @return 资源ID
     */
    private static int getIconResourceId(String iconIdentifier) {
        // 这里应该根据实际的图标资源进行映射
        // 现在只是示例，实际应该根据iconIdentifier动态加载
        if (iconIdentifier == null) {
            return R.drawable.ic_category_other;
        }
        
        switch (iconIdentifier) {
            case "res:ic_category_food":
                return R.drawable.ic_category_food;
            case "res:ic_category_transport":
                return R.drawable.ic_category_transport;
            case "res:ic_category_shopping":
                return R.drawable.ic_category_shopping;
            case "res:ic_category_entertainment":
                return R.drawable.ic_category_entertainment;
            case "res:ic_category_medical":
                return R.drawable.ic_category_medical;
            case "res:ic_category_education":
                return R.drawable.ic_category_education;
            case "res:ic_category_life":
                return R.drawable.ic_category_life;
            case "res:ic_category_salary":
                return R.drawable.ic_category_salary;
            case "res:ic_category_bonus":
                return R.drawable.ic_category_bonus;
            case "res:ic_category_investment":
                return R.drawable.ic_category_investment;
            case "res:ic_category_refund":
                return R.drawable.ic_category_refund;
            default:
                return R.drawable.ic_category_other;
        }
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
                // 使用主题色作为选中背景
                itemView.setBackgroundColor(context.getResources().getColor(R.color.selected_category_background));
                tvCategoryName.setTextColor(context.getResources().getColor(R.color.primary_color));
            } else {
                itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
                tvCategoryName.setTextColor(context.getResources().getColor(R.color.text_primary));
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