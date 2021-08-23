package com.example.haha.customview.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;

/**
 * Created by haha on 2020/3/1.
 */

public class SystemUtils {
    public static String getProcessName(Context context){
        int pid = Process.myPid();
        ActivityManager mActivityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : mActivityManager.getRunningAppProcesses()){
            if (appProcessInfo.pid == pid){
                return appProcessInfo.processName;
            }
        }
        return null;
    }
}
