package com.example.xp_pc_xxx.testactivitycyclelifecallbacks.ui;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.example.xp_pc_xxx.testactivitycyclelifecallbacks.hook.Hooker;
import com.example.xp_pc_xxx.testactivitycyclelifecallbacks.utils.DataCollector;

import java.lang.reflect.InvocationTargetException;

/**
 * @author XP-PC-XXX
 * @date 2017/11/14
 */


public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        DataCollector dataCollector = DataCollector.getInstance();
        dataCollector.hookApplication();
        //DataCollector获取Activity的生存时间
        dataCollector.collectActivityShowTime(this, DataCollector.callbacks);
    }


}