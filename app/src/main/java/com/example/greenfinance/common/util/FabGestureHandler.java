package com.example.greenfinance.common.util;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.greenfinance.presentation.MainActivity;

import lombok.Getter;

/**
 * 浮动按钮手势处理器
 * 处理浮动按钮的点击和滑动手势
 */
public class FabGestureHandler implements View.OnTouchListener {
    private static final String TAG = "FabGestureHandler";
    private final GestureDetector gestureDetector;
    private final Context context;
    /**
     * -- GETTER --
     *  获取当前附加的视图
     *
     */
    @Getter
    private View attachedView;

    public FabGestureHandler(Context context) {
        this.context = context;
        
        // 创建滑动监听器
        SwipeGestureDetector.OnSwipeListener swipeListener = new SwipeGestureDetector.OnSwipeListener() {
            @Override
            public void onSwipeLeft() {
                // 左滑进入截图记账（根据新规范）
                Log.d(TAG, "Swipe left detected");
                if (context instanceof MainActivity) {
                    ((MainActivity) context).onFabSwipeLeft();
                }
            }

            @Override
            public void onSwipeRight() {
                // 右滑进入手动记账（根据新规范）
                Log.d(TAG, "Swipe right detected");
                if (context instanceof MainActivity) {
                    ((MainActivity) context).onFabSwipeRight();
                }
            }
            
            @Override
            public void onSwipeUp() {
                // 上滑进入文字记账（根据新规范）
                Log.d(TAG, "Swipe up detected");
                if (context instanceof MainActivity) {
                    ((MainActivity) context).onFabSwipeUp();
                }
            }
            
            @Override
            public void onSwipeDown() {
                // 下滑暂不处理
                Log.d(TAG, "Swipe down detected - no action");
                if (context instanceof MainActivity) {
                    ((MainActivity) context).onFabSwipeDown();
                }
            }

            @Override
            public void onClick() {
                // 点击显示选项菜单
                Log.d(TAG, "Click detected");
                if (context instanceof FabGestureHandler.OnFabClickListener) {
                    ((FabGestureHandler.OnFabClickListener) context).onFabClick();
                }
            }
        };
        
        // 创建手势检测器
        this.gestureDetector = new GestureDetector(context, new SwipeGestureDetector(swipeListener));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == null || event == null) {
            Log.w(TAG, "View or MotionEvent is null");
            return false;
        }

        // 保存附加的视图引用
        if (this.attachedView == null) {
            this.attachedView = v;
        }

        // 处理手势事件
        boolean result = gestureDetector.onTouchEvent(event);
        
        // 如果是ACTION_UP事件且没有被手势检测器处理，则认为是点击事件
        if (event.getAction() == MotionEvent.ACTION_UP && !result) {
            Log.d(TAG, "Click event detected via ACTION_UP");
            if (context instanceof OnFabClickListener) {
                ((OnFabClickListener) context).onFabClick();
                // 修复：在检测到点击时调用performClick以满足无障碍性要求
                if (v != null) {
                    v.performClick();
                }
                return true;
            }
        }

        return result;
    }
    
    /**
     * FAB点击事件监听器接口
     */
    public interface OnFabClickListener {
        void onFabClick();
    }
}