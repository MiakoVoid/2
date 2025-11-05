package com.example.greenfinance.common.util;

import android.content.Context;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.greenfinance.presentation.bill.view.ManualBillingActivity;
import com.example.greenfinance.presentation.bill.view.ScreenshotBillingActivity;

/**
 * 浮动按钮手势处理器
 * 处理浮动按钮的点击和滑动手势
 */
public class FabGestureHandler implements View.OnTouchListener {
    private final GestureDetector gestureDetector;
    private final Context context;
    private View attachedView;

    public FabGestureHandler(Context context) {
        this.context = context;
        
        // 创建滑动监听器
        SwipeGestureDetector.OnSwipeListener swipeListener = new SwipeGestureDetector.OnSwipeListener() {
            @Override
            public void onSwipeLeft() {
                // 左滑进入截图记账
                startScreenshotBilling();
            }

            @Override
            public void onSwipeRight() {
                // 右滑进入普通记账
                startManualBilling();
            }

            @Override
            public void onClick() {
                // 点击进入文字记账
                // 通过接口回调处理点击事件
                if (context instanceof OnFabClickListener) {
                    ((OnFabClickListener) context).onFabClick();
                }
            }
        };
        
        // 创建手势检测器
        this.gestureDetector = new GestureDetector(context, new SwipeGestureDetector(swipeListener));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == null || event == null) {
            return false;
        }

        // 保存附加的视图引用
        if (this.attachedView == null) {
            this.attachedView = v;
        }

        // 处理手势事件
        boolean handled = gestureDetector.onTouchEvent(event);

        // 当检测到ACTION_UP事件时调用performClick以确保可访问性
        if (event.getAction() == MotionEvent.ACTION_UP) {
            v.performClick();
        }

        return handled;
    }

    /**
     * 获取当前附加的视图
     * @return 附加的视图
     */
    public View getAttachedView() {
        return attachedView;
    }

    /**
     * 手动触发点击事件
     */
    public void performClick() {
        if (attachedView != null) {
            attachedView.performClick();
        }
    }

    /**
     * 设置附加视图
     * @param view 要附加的视图
     */
    public void setAttachedView(View view) {
        this.attachedView = view;
    }
    
    /**
     * 启动截图记账
     */
    private void startScreenshotBilling() {
        if (context != null) {
            Intent intent = new Intent(context, ScreenshotBillingActivity.class);
            context.startActivity(intent);
        }
    }
    
    /**
     * 启动手动记账
     */
    private void startManualBilling() {
        if (context != null) {
            Intent intent = new Intent(context, ManualBillingActivity.class);
            context.startActivity(intent);
        }
    }
    
    /**
     * FAB点击事件监听器接口
     */
    public interface OnFabClickListener {
        void onFabClick();
    }
}