package com.example.xp_pc_xxx.testactivitycyclelifecallbacks.hook;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by XP-PC-XXX on 2017/11/16.
 */

public class Hooker {
    private static final  String TAG = "Hooker";

    /**
     * hook LayoutInflater
     * 使得用自己的LayoutInflater代替PhoneInflater
     */
    public static void hookLayoutInflater() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //反射拿到ServiceFetcher类
        Class<?> ServiceFetcher = Class.forName("android.app.SystemServiceRegistry$ServiceFetcher");
        //动态代理拿到ServiceFetcherImpl，否则直接Class.newInstance会出错
        Object ServiceFetcherImpl = Proxy.newProxyInstance(Hooker.class.getClassLoader(),new Class[]{ServiceFetcher},new ServiceFetcherHandler());
        //利用反射机制拿到SystemRegistry中的registerService方法
        Class<?> SystemServiceRegistry = Class.forName("android.app.SystemServiceRegistry");
        Method registerService = SystemServiceRegistry.getDeclaredMethod("registerService",
                String.class, MyLayoutInflater.class.getClass(), ServiceFetcher);
        registerService.setAccessible(true);
        //调用registerService方法，当调用registerService的时候，选择将PhoneInflater替换成自定义的Inflater
        registerService.invoke(SystemServiceRegistry,
                new Object[]{Context.LAYOUT_INFLATER_SERVICE, MyLayoutInflater.class, ServiceFetcherImpl});


    }
}
