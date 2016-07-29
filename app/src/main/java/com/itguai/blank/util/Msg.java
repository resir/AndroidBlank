package com.itguai.blank.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.Toast;
import com.itguai.blank.App;

/**
 * Created by jianyuncheng on 15/1/22.
 */
public class Msg {

    public static void toast(String info) {
        Toast.makeText(App.getIns(), info, Toast.LENGTH_SHORT).show();
    }

    public static void confirm(Context c, String info, DialogInterface.OnClickListener yes, DialogInterface.OnClickListener no) {
        new AlertDialog.Builder(c, AlertDialog.THEME_HOLO_LIGHT)
                .setMessage(info)//设置显示的内容
                .setPositiveButton("确定", yes)
                .setCancelable(false)
                .setNegativeButton("取消", no).show();
    }

    public static void alert(Context c, String info) {
        new AlertDialog.Builder(c, AlertDialog.THEME_HOLO_LIGHT)
                .setMessage(info)//设置显示的内容
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    public static void alert(Context c, String info, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(c, AlertDialog.THEME_HOLO_LIGHT)
                .setMessage(info)//设置显示的内容
                .setPositiveButton("确定", listener)
                .setCancelable(false)
                .show();
    }

    public static ProgressDialog progress(Activity activity, String hintText) {
        return progress(activity, hintText, false);
    }

    public static ProgressDialog progress(Activity activity, String hintText, boolean cancelable) {
        Activity mActivity;
        if (activity.getParent() != null) {
            mActivity = activity.getParent();
            if (mActivity.getParent() != null) {
                mActivity = mActivity.getParent();
            }
        } else {
            mActivity = activity;
        }
        final Activity finalActivity = mActivity;
        if (finalActivity.isFinishing()) {
            return null;
        }
        ProgressDialog window = ProgressDialog.show(finalActivity, "", hintText);
        window.getWindow().setGravity(Gravity.CENTER);

        window.setCancelable(cancelable);
        window.show();
        return window;
    }

}
