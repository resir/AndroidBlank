package com.itguai.biz.util;

import android.text.TextUtils;
import android.util.Log;

/**
 *
 */
public class ULog {
    private final static String TAG = "log";

    public static void d(String info) {
        if (isDebug()) {
            if (!TextUtils.isEmpty(info)) {
                Log.d(TAG, info);
            }
        }
    }

    public static void w(String tag, String info) {
        if (isDebug()) {
            if (!TextUtils.isEmpty(info)) {
                Log.w(tag, info);
            }
        }
    }

    public static void i(String info) {
        if (isDebug()) {
            if (!TextUtils.isEmpty(info)) {
                Log.i(TAG, info);
            }
        }
    }

    public static void d(String info, String subTAG) {
        if (isDebug()) {
            Log.d(TAG + "" + subTAG, info);
        }
    }

    public static void e(String info) {
        if (isDebug()) {
            if (!TextUtils.isEmpty(info)) {
                Log.e(TAG, info);
            }
        }
    }

    public static boolean isDebug() {
//        return !BuildConfig.RELEASE;
        return  true;
    }

}