package com.example.greenfinance.common.binding;

import androidx.databinding.BindingAdapter;

import com.google.android.material.progressindicator.LinearProgressIndicator;

public class BindingAdapters {

    @BindingAdapter("android:progress")
    public static void setProgress(LinearProgressIndicator progressBar, int progress) {
        progressBar.setProgress(progress);
    }

    @BindingAdapter("indicatorColor")
    public static void setIndicatorColor(LinearProgressIndicator progressBar, int color) {
        if (color != 0) {
            progressBar.setIndicatorColor(color);
        }
    }
}