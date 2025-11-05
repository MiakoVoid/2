package com.example.greenfinance.presentation.report.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.greenfinance.R;
import com.example.greenfinance.common.util.FabGestureHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ReportFragment extends Fragment {
    private FabGestureHandler fabGestureHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupFabGestureHandler();
    }

    private void setupFabGestureHandler() {
        if (getActivity() != null) {
            // 获取MainActivity中的FAB
            FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_bill);
            View fabContainer = getActivity().findViewById(R.id.fab_container);
            
            // 创建手势处理器
            fabGestureHandler = new FabGestureHandler(getActivity());
            
            // 设置触摸监听器
            View.OnTouchListener touchListener = fabGestureHandler;
            
            // 为FAB和容器设置触摸监听器
            if (fab != null) {
                fab.setOnTouchListener(touchListener);
                fabGestureHandler.setAttachedView(fab);
            }
            
            if (fabContainer != null) {
                fabContainer.setOnTouchListener(touchListener);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 清理手势处理器
        if (fabGestureHandler != null) {
            fabGestureHandler.setAttachedView(null);
        }
    }
}