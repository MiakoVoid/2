package com.example.greenfinance.common.util;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

/**
 * 滑动手势检测器
 * 用于检测在浮动按钮上的滑动手势
 */
public class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private static final String TAG = "SwipeGestureDetector";
    // 降低阈值以提高检测灵敏度
    private static final int SWIPE_THRESHOLD = 20;
    private static final int SWIPE_VELOCITY_THRESHOLD = 20;
    
    public interface OnSwipeListener {
        void onSwipeLeft();
        void onSwipeRight();
        void onSwipeUp();
        void onSwipeDown();
        void onClick();
    }
    
    private final OnSwipeListener swipeListener;
    
    public SwipeGestureDetector(OnSwipeListener listener) {
        this.swipeListener = listener;
    }
    
    @Override
    public boolean onFling(MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        // 添加空值检查
        if (e1 == null) {
            Log.d(TAG, "MotionEvent is null");
            return false;
        }
        
        float diffY = e2.getY() - e1.getY();
        float diffX = e2.getX() - e1.getX();
        
        // 记录手势数据用于调试
        Log.d(TAG, "onFling: diffX=" + diffX + ", diffY=" + diffY + 
                ", velocityX=" + velocityX + ", velocityY=" + velocityY);
        
        // 判断是水平滑动还是垂直滑动
        if (Math.abs(diffX) > Math.abs(diffY)) {
            // 水平滑动
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    // 右滑
                    Log.d(TAG, "Swipe Right detected");
                    if (swipeListener != null) {
                        swipeListener.onSwipeRight();
                    }
                } else {
                    // 左滑
                    Log.d(TAG, "Swipe Left detected");
                    if (swipeListener != null) {
                        swipeListener.onSwipeLeft();
                    }
                }
                return true;
            }
        } else {
            // 垂直滑动
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY < 0) {
                    // 上滑
                    Log.d(TAG, "Swipe Up detected");
                    if (swipeListener != null) {
                        swipeListener.onSwipeUp();
                    }
                } else {
                    // 下滑
                    Log.d(TAG, "Swipe Down detected");
                    if (swipeListener != null) {
                        swipeListener.onSwipeDown();
                    }
                }
                return true;
            }
        }
        
        Log.d(TAG, "Swipe not detected");
        return false;
    }
    
    @Override
    public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
        Log.d(TAG, "onSingleTapConfirmed detected");
        if (swipeListener != null) {
            swipeListener.onClick();
        }
        return true;
    }
    
    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        // 必须返回true才能接收后续事件
        Log.d(TAG, "onDown detected");
        return true;
    }
}