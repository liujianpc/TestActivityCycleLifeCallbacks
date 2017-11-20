package com.example.xp_pc_xxx.testactivitycyclelifecallbacks.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xp_pc_xxx.testactivitycyclelifecallbacks.hook.Hooker;

import java.lang.reflect.InvocationTargetException;

/**
 * Date: 2017/11/17
 * Created by XP-PC-XXX
 *
 * @author XP-PC-XXX
 */

public class DataCollector {
    private static DataCollector instance = null;

    private DataCollector() {
    }

    public static DataCollector getInstance() {
        synchronized (DataCollector.class) {
            if (instance == null) {
                instance = new DataCollector();
            }
            return instance;
        }
    }

    public static void onViewClick(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();

        if (parent instanceof ListView) {
            ListView listView = (ListView) parent;
            int position = listView.getPositionForView(view);
            ToastUtil.showToast(view.getContext(), listView.getTag().toString() + "[" + position + "]");

        } else if (parent instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view.getParent();
            int position = recyclerView.getChildAdapterPosition(view);
            ToastUtil.showToast(view.getContext(), recyclerView.getTag().toString() + "[" + position + "]");

        } else if (parent instanceof GridView) {
            GridView gridView = (GridView) view.getParent();
            int position = gridView.getPositionForView(view);
            ToastUtil.showToast(view.getContext(), gridView.getTag().toString() + "[" + position + "]");

        } else if (parent instanceof ViewPager) {
            ViewPager pager = (ViewPager) view.getParent();
            int position = pager.getCurrentItem();
            ToastUtil.showToast(view.getContext(), pager.getTag().toString() + "[" + position + "]");

        } else if (view instanceof TextView) {
            ToastUtil.showToast(view.getContext(), ((TextView) view).getText().toString());
        }
    }

    /**
     * 给Application初始化的时候注册ActivityLifeCycleBacks的接口
     * 在DataCollector中默认初始化成track页面Activity的显示时间
     */
    public static Application.ActivityLifecycleCallbacks callbacks = new Application.ActivityLifecycleCallbacks() {

        long startTime, endTime, showTime;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            startTime = System.currentTimeMillis();

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            endTime = System.currentTimeMillis();
            showTime = endTime - startTime;
            Log.d(activity.getLocalClassName(), "this activity showTime is " + showTime);


        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    public void collectActivityShowTime(Application application, Application.ActivityLifecycleCallbacks callbacks) {
        application.registerActivityLifecycleCallbacks(callbacks);
    }

    /**
     * hook替换掉registerService的phoneInflater
     */
    public void hookApplication() {
        try {
            Hooker.hookLayoutInflater();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
