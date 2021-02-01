package com.moredian.entrance.porthelper;

import android.app.Application;
import android.content.Context;


import android_serialport_api.SerialPortUtils;

/**
 * description ：
 * author : scy
 * email : 1797484636@qq.com
 * date : 2019/7/25 14:53
 */
public class MainApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        MyCrashHandler crashHandler = MyCrashHandler.instance();
        crashHandler.init(getApplicationContext());
    }
}
