package com.example.findaroomver2.retrofit;

import android.os.Handler;
import android.os.Looper;

public class AndroidUtilities {
    private static Handler uiHandler = new Handler(Looper.getMainLooper());

    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        if (delay == 0) {
            uiHandler.post(runnable);
        } else {
            uiHandler.postDelayed(runnable, delay);
        }
    }
}
