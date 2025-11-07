package com.example.greenfinance.presentation.bill.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenfinance.R;
import com.example.greenfinance.data.model.Bill;
import com.example.greenfinance.presentation.home.adapter.CategoryAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ManualBillingActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryClickListener {
    
    // UI组件
    private ImageView ivBack;
    private TextView tvExpense, tvIncome, tvUpdate;
    private RecyclerView rvCategories;
    private EditText etMerchant, etRemark;
    private TextView tvDate, tvAmount;
    private Button btnNumber0, btnNumber1, btnNumber2, btnNumber3, btnNumber4, 
                   btnNumber5, btnNumber6, btnNumber7, btnNumber8, btnNumber9;
    private Button btnAdd, btnSubtract, btnDecimalPoint, btnRecordAgain, btnSave;
    private ImageButton btnDelete;
    private View layoutDatePicker;
    
    // 业务变量
    private boolean isExpense = true; // true为支出，false为收入
    private StringBuilder amountBuilder = new StringBuilder("0.00");
    private Calendar selectedDate = Calendar.getInstance();
    private CategoryAdapter categoryAdapter;
    private long selectedCategoryId = -1;
    private Long selectedSubCategoryId = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        
        initViews();
        setupListeners();
        setupCategories();
    }
    
    private void initViews() {
        // 初始化顶部导航组件
        ivBack = findViewById(R.id.iv_back);
        tvExpense = findViewById(R.id.tv_expense);
        tvIncome = findViewById(R.id.tv_income);
        tvUpdate = findViewById(R.id.tv_update);
        
        // 初始化分类列表
        rvCategories = findViewById(R.id.rv_categories);
        
        // 初始化商户和备注输入框
        etMerchant = findViewById(R.id.et_merchant);
        etRemark = findViewById(R.id.et_remark);
        
        // 初始化日期和金额显示
        tvDate = findViewById(R.id.tv_date);
        tvAmount = findViewById(R.id.tv_amount);
        
        // 初始化数字键盘
        btnNumber0 = findViewById(R.id.btn_number_0);
        btnNumber1 = findViewById(R.id.btn_number_1);
        btnNumber2 = findViewById(R.id.btn_number_2);
        btnNumber3 = findViewById(R.id.btn_number_3);
        btnNumber4 = findViewById(R.id.btn_number_4);
        btnNumber5 = findViewById(R.id.btn_number_5);
        btnNumber6 = findViewById(R.id.btn_number_6);
        btnNumber7 = findViewById(R.id.btn_number_7);
        btnNumber8 = findViewById(R.id.btn_number_8);
        btnNumber9 = findViewById(R.id.btn_number_9);
        btnAdd = findViewById(R.id.btn_add);
        btnSubtract = findViewById(R.id.btn_subtract);
        btnDecimalPoint = findViewById(R.id.btn_decimal_point);
        btnRecordAgain = findViewById(R.id.btn_record_again);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);
        
        // 初始化日期选择布局
        layoutDatePicker = findViewById(R.id.layout_date_picker);
        
        // 更新日期显示
        updateDateDisplay();
    }
    
    private void setupListeners() {
        // 返回按钮
        ivBack.setOnClickListener(v -> finish());
        
        // 收支类型切换
        tvExpense.setOnClickListener(v -> switchToExpense());
        tvIncome.setOnClickListener(v -> switchToIncome());
        
        // 编辑按钮（暂时不实现）
        tvUpdate.setOnClickListener(v -> {
            Toast.makeText(this, "编辑功能尚未实现", Toast.LENGTH_SHORT).show();
        });
        
        // 数字按钮
        btnNumber0.setOnClickListener(v -> appendNumber("0"));
        btnNumber1.setOnClickListener(v -> appendNumber("1"));
        btnNumber2.setOnClickListener(v -> appendNumber("2"));
        btnNumber3.setOnClickListener(v -> appendNumber("3"));
        btnNumber4.setOnClickListener(v -> appendNumber("4"));
        btnNumber5.setOnClickListener(v -> appendNumber("5"));
        btnNumber6.setOnClickListener(v -> appendNumber("6"));
        btnNumber7.setOnClickListener(v -> appendNumber("7"));
        btnNumber8.setOnClickListener(v -> appendNumber("8"));
        btnNumber9.setOnClickListener(v -> appendNumber("9"));
        
        // 小数点按钮
        btnDecimalPoint.setOnClickListener(v -> appendDecimalPoint());
        
        // 删除按钮
        btnDelete.setOnClickListener(v -> deleteLastDigit());
        
        // 再记一笔按钮
        btnRecordAgain.setOnClickListener(v -> resetForNewRecord());
        
        // 保存按钮
        btnSave.setOnClickListener(v -> saveBill());
        
        // 日期选择
        layoutDatePicker.setOnClickListener(v -> showDatePicker());
    }
    
    private void setupCategories() {
        // 设置分类列表
        categoryAdapter = new CategoryAdapter(this, new ArrayList<>());
        categoryAdapter.setOnCategoryClickListener(this);
        
        // 使用GridLayoutManager，每行显示4个分类
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        rvCategories.setLayoutManager(layoutManager);
        rvCategories.setAdapter(categoryAdapter);
        
        // 加载分类数据（模拟数据）
        loadCategories();
    }
    
    private void loadCategories() {
        // TODO: 从数据库或网络加载分类数据
        // 这里使用模拟数据
        List<CategoryAdapter.CategoryItem> categories = new ArrayList<>();
        
        if (isExpense) {
            // 支出分类
            categories.add(new CategoryAdapter.CategoryItem(1, "餐饮", R.drawable.ic_category_food));
            categories.add(new CategoryAdapter.CategoryItem(2, "交通", R.drawable.ic_category_transport));
            categories.add(new CategoryAdapter.CategoryItem(3, "购物", R.drawable.ic_category_shopping));
            categories.add(new CategoryAdapter.CategoryItem(4, "娱乐", R.drawable.ic_category_entertainment));
            categories.add(new CategoryAdapter.CategoryItem(5, "医疗", R.drawable.ic_category_medical));
            categories.add(new CategoryAdapter.CategoryItem(6, "教育", R.drawable.ic_category_education));
            categories.add(new CategoryAdapter.CategoryItem(7, "生活", R.drawable.ic_category_life));
            categories.add(new CategoryAdapter.CategoryItem(8, "其他", R.drawable.ic_category_other));
        } else {
            // 收入分类
            categories.add(new CategoryAdapter.CategoryItem(101, "工资", R.drawable.ic_category_salary));
            categories.add(new CategoryAdapter.CategoryItem(102, "奖金", R.drawable.ic_category_bonus));
            categories.add(new CategoryAdapter.CategoryItem(103, "投资", R.drawable.ic_category_investment));
            categories.add(new CategoryAdapter.CategoryItem(104, "退款", R.drawable.ic_category_refund));
            categories.add(new CategoryAdapter.CategoryItem(105, "其他", R.drawable.ic_category_other));
        }
        
        categoryAdapter.updateCategories(categories);
    }
    
    private void switchToExpense() {
        if (!isExpense) {
            isExpense = true;
            tvExpense.setTextColor(getResources().getColor(R.color.expense_color));
            tvIncome.setTextColor(getResources().getColor(R.color.text_secondary));
            tvAmount.setTextColor(getResources().getColor(R.color.expense_color));
            
            // 重新加载支出分类
            loadCategories();
        }
    }
    
    private void switchToIncome() {
        if (isExpense) {
            isExpense = false;
            tvIncome.setTextColor(getResources().getColor(R.color.income_color));
            tvExpense.setTextColor(getResources().getColor(R.color.text_secondary));
            tvAmount.setTextColor(getResources().getColor(R.color.income_color));
            
            // 重新加载收入分类
            loadCategories();
        }
    }
    
    private void appendNumber(String number) {
        String current = amountBuilder.toString();
        
        if (current.equals("0.00")) {
            // 如果是初始状态，直接替换
            amountBuilder = new StringBuilder(number);
        } else {
            // 检查小数点后是否已经有两位数字
            int dotIndex = current.indexOf(".");
            if (dotIndex != -1 && current.length() - dotIndex > 2) {
                // 已经有两位小数，不能再添加
                return;
            }
            
            // 添加数字
            amountBuilder.append(number);
        }
        
        // 更新显示
        updateAmountDisplay();
    }
    
    private void appendDecimalPoint() {
        String current = amountBuilder.toString();
        
        // 检查是否已经包含小数点
        if (!current.contains(".")) {
            amountBuilder.append(".");
            updateAmountDisplay();
        }
    }
    
    private void deleteLastDigit() {
        String current = amountBuilder.toString();
        
        if (current.length() > 1) {
            // 删除最后一个字符
            amountBuilder.deleteCharAt(amountBuilder.length() - 1);
        } else {
            // 如果只剩一个字符，替换为0.00
            amountBuilder = new StringBuilder("0.00");
        }
        
        updateAmountDisplay();
    }
    
    private void updateAmountDisplay() {
        String amountStr = amountBuilder.toString();
        
        // 确保格式正确
        if (!amountStr.contains(".")) {
            amountStr += ".00";
        } else {
            int dotIndex = amountStr.indexOf(".");
            if (dotIndex == amountStr.length() - 1) {
                // 小数点在最后一位
                amountStr += "00";
            } else if (dotIndex == amountStr.length() - 2) {
                // 小数点后只有一位数字
                amountStr += "0";
            } else if (dotIndex < amountStr.length() - 3) {
                // 小数点后超过两位数字，截取前两位
                amountStr = amountStr.substring(0, dotIndex + 3);
            }
        }
        
        tvAmount.setText(amountStr);
    }
    
    private void resetForNewRecord() {
        // 重置金额
        amountBuilder = new StringBuilder("0.00");
        updateAmountDisplay();
        
        // 清空输入框
        etMerchant.setText("");
        etRemark.setText("");
        
        // 重置选中分类
        selectedCategoryId = -1;
        selectedSubCategoryId = null;
        categoryAdapter.setSelectedCategory(-1);
        
        // 重置日期为今天
        selectedDate = Calendar.getInstance();
        updateDateDisplay();
        
        Toast.makeText(this, "已重置，可以开始新记录", Toast.LENGTH_SHORT).show();
    }
    
    private void saveBill() {
        String amountStr = tvAmount.getText().toString();
        String merchant = etMerchant.getText().toString().trim();
        String remark = etRemark.getText().toString().trim();
        
        if (selectedCategoryId == -1) {
            Toast.makeText(this, "请选择分类", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (amountStr.equals("0.00")) {
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
            return;
        }
        
        try {
            // 创建账单对象
            Bill bill = new Bill();
            bill.setAmount(new BigDecimal(amountStr));
            bill.setOriginalAmount(new BigDecimal(amountStr));
            bill.setRefundAmount(BigDecimal.ZERO);
            bill.setType(isExpense ? 1 : 2); // 1-支出，2-收入
            bill.setCategoryId(selectedCategoryId);
            bill.setSubCategoryId(selectedSubCategoryId);
            bill.setMerchant(merchant.isEmpty() ? "未填写" : merchant);
            bill.setRemark(remark);
            bill.setBillTime(selectedDate.getTime());
            bill.setPaymentMethod("手动记账");
            bill.setCreateTime(new Date());
            bill.setUpdateTime(new Date());
            
            // TODO: 保存账单到数据库或服务器
            // 这里只是模拟保存成功
            Toast.makeText(this, "账单保存成功", Toast.LENGTH_SHORT).show();
            
            // 返回上一页
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "保存失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    selectedDate.set(Calendar.YEAR, year);
                    selectedDate.set(Calendar.MONTH, month);
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDateDisplay();
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    
    private void updateDateDisplay() {
        // 获取今天的日期
        Calendar today = Calendar.getInstance();
        
        // 比较是否是今天
        if (selectedDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            selectedDate.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            tvDate.setText("今天");
        } else {
            // 显示选择的日期
            String dateStr = (selectedDate.get(Calendar.MONTH) + 1) + "月" + 
                            selectedDate.get(Calendar.DAY_OF_MONTH) + "日";
            tvDate.setText(dateStr);
        }
    }
    
    @Override
    public void onCategoryClick(long categoryId, String categoryName) {
        selectedCategoryId = categoryId;
        // TODO: 加载并显示子分类
        Toast.makeText(this, "选择了分类: " + categoryName, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}