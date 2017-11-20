package com.example.xp_pc_xxx.testactivitycyclelifecallbacks.hook;

import android.content.Context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Date: 2017/11/16
 * Created by XP-PC-XXX
 */

class ServiceFetcherHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return new MyLayoutInflater((Context) args[0]);
    }
}
