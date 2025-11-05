package com.example.greenfinance.presentation.bill.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.greenfinance.R;
import com.google.android.material.textfield.TextInputEditText;

public class TextBillingDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 使用对话框布局
        View view = inflater.inflate(R.layout.dialog_text_billing, container, false);
        initViews(view);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // 创建自定义对话框
        return new Dialog(requireContext(), getTheme());
    }

    private void initViews(View view) {
        // 初始化视图组件
        TextInputEditText etTextInput = view.findViewById(R.id.et_text_input);
        ImageView ivCalendar = view.findViewById(R.id.iv_calendar);
        TextView tvSelectedDate = view.findViewById(R.id.tv_selected_date);
        Button btnConfirm = view.findViewById(R.id.btn_confirm);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        // 检查视图是否为空
        if (btnConfirm == null || btnCancel == null || ivCalendar == null) {
            return;
        }

        // 设置事件监听器
        btnConfirm.setOnClickListener(v -> {
            // 处理文本记账逻辑
            if (etTextInput != null && etTextInput.getText() != null) {
                String inputText = etTextInput.getText().toString();
                // TODO: 处理输入的文本并保存账单
            }
            dismiss(); // 关闭对话框
        });

        btnCancel.setOnClickListener(v -> {
            dismiss(); // 关闭对话框
        });

        ivCalendar.setOnClickListener(v -> {
            // TODO: 显示日期选择器
        });

        // 为防止空指针异常，检查组件是否为空
        if (etTextInput == null) {
            return;
        }
    }
}