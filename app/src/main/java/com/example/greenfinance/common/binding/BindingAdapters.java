package com.example.greenfinance.common.binding;

import android.widget.ProgressBar;
import androidx.databinding.BindingAdapter;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class BindingAdapters {
    
    @BindingAdapter("android:progress")
    public static void setProgress(LinearProgressIndicator progressBar, int progress) {
        progressBar.setProgress(progress);
    }
    
    @BindingAdapter("app:indicatorColor")
    public static void setIndicatorColor(LinearProgressIndicator progressBar, int color) {
        progressBar.setIndicatorColor(color);
    }
}