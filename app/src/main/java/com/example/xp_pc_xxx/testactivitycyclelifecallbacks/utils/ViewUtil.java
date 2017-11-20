package com.example.xp_pc_xxx.testactivitycyclelifecallbacks.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XP-PC-XXX on 2017/11/14.
 */

public class ViewUtil {
    public static List<String> getViewPath(View view){
        List<String> viewPath = new ArrayList<>();
        if (view instanceof ViewGroup){
            return viewPath;
        }
        while(view.getParent() != null){
        }
        return null;
    }

}
