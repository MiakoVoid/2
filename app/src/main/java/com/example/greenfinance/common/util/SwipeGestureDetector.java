package com.example.greenfinance.common.util;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * 滑动手势检测器
 * 用于检测在浮动按钮上的滑动手势
 */
public class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    
    public interface OnSwipeListener {
        void onSwipeLeft();
        void onSwipeRight();
        void onClick();
    }
    
    private final OnSwipeListener swipeListener;
    
    public SwipeGestureDetector(OnSwipeListener listener) {
        this.swipeListener = listener;
    }
    
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1 == null || e2 == null) {
            return false;
        }
        
        float diffY = e2.getY() - e1.getY();
        float diffX = e2.getX() - e1.getX();
        
        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    // 右滑
                    if (swipeListener != null) {
                        swipeListener.onSwipeRight();
                    }
                } else {
                    // 左滑
                    if (swipeListener != null) {
                        swipeListener.onSwipeLeft();
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (swipeListener != null) {
            swipeListener.onClick();
        }
        return true;
    }
}