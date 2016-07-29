package com.itguai.biz;

import android.app.Application;
import android.content.Context;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator on 2016.07.27.
 */
public class App extends Application {
    public static  App instance;
    public static Context getIns() {
        return instance;
    }

    @Override public void onCreate() {
        super.onCreate();
        instance = this;
        LeakCanary.install(this);
    }
}