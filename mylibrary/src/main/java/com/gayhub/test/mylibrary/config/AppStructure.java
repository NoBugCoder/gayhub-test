/**
 * <pre>
 * Copyright 2014-2019 Soulwolf AppStructure
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </pre>
 */
package com.gayhub.test.mylibrary.config;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;


/**
 * 应用程序构造器
 * <p/>
 * author :xing
 * email  :
 */
public final class AppStructure {

    private static boolean isDebug;//

    private static final String LOG_TAG = "AppStructure:";

    private static AppStructure mAppStructure;

    private Context mContext;

    private int mScreenWidth;

    private int mScreenHeight;
    private float mScreenDensity;


    private Handler myHandler=new Handler();

    public Handler getHandler(){
        return myHandler;
    }

    public static void init(Context context) {
        if (mAppStructure == null) {
            synchronized (AppStructure.class) {
                if (mAppStructure == null) {
                    mAppStructure = new AppStructure(context);
                }
            }
        }
    }



    public static AppStructure getInstance() {
        if (mAppStructure == null) {
            throw new IllegalStateException("AppStructure uninitialized!");
        }
        return mAppStructure;
    }

    public static boolean isDebug() {
        return isDebug;

    }

    AppStructure(Context context) {
        this.mContext = context;
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(metrics);
        this.mScreenWidth = metrics.widthPixels;
        this.mScreenHeight = metrics.heightPixels;
        mScreenDensity=metrics.density;
//        isDebug = BuildConfig.DEBUG;//条件编译   add xing 2015/12
    }

    public float getDensity(){
        return mScreenDensity;
    }



    public Context getContext() {
        return mContext;
    }

    public int getScreenWidth() {
        return mScreenWidth;
    }

    public int getScreenHeight() {
        return mScreenHeight;
    }

    public File getCacheDir() {
        return mContext.getCacheDir();
    }


    /**
     * 获取程序版本号
     */
    public int getAppVersionCode() {
        try {
            PackageManager manager = getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getContext().getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取程序版本号
     */
    public String getAppVersion() {
        try {
            PackageManager manager = getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getContext().getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
