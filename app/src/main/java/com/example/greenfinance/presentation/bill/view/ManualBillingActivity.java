package com.example.greenfinance.presentation.bill.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.greenfinance.R;

public class ManualBillingActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        
        // 手动记账页面不需要设置底部导航
    }

    
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}