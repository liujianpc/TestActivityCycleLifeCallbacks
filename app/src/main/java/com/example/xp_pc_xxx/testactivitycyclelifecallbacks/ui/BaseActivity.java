package com.example.xp_pc_xxx.testactivitycyclelifecallbacks.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * @author XP-PC-XXX
 * @date 2017/11/14
 */

public class BaseActivity extends AppCompatActivity {
    List<View> views;
    List<String> viewPath = new ArrayList<>();
    long startTime;
    long endTime;
    DispatchTouchEventable dispatchTouchEventable;

    public void setDispatchTouchEventable(DispatchTouchEventable dispatchTouchEventable) {
        this.dispatchTouchEventable = dispatchTouchEventable;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTime = System.currentTimeMillis();
        //同时在onCreate中初始化一个需要埋点的列表，该列表时由服务端下发的
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (views != null) {
            views.clear();
        }
        View rootView = getWindow().getDecorView().getRootView();
        //在viewTree中获取所有view
        getAllViews(rootView);
        //获得view的详情
        LinkedList<String> nodeList = new LinkedList<>();
        //获得所有view的路径:格式为：viewGroup0--->viewGroup1--->viewGroup2---->view
        getViewPathWithTree(rootView, nodeList);

    }

    /**
     * 获得ViewTree中的View
     *
     * @param viewGroup 根节点
     * @return viewTree中的view
     */

    public void getAllViews(View viewGroup) {
        if (views == null) {
            views = new ArrayList<>();
        }
        if (viewGroup == null) {
            return;
        }
        if (!(viewGroup instanceof ViewGroup)) {
            views.add(viewGroup);

        } else {
            int childCount = ((ViewGroup) viewGroup).getChildCount();
            for (int i = 0; i < childCount; i++) {
                getAllViews(((ViewGroup) viewGroup).getChildAt(i));
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        endTime = System.currentTimeMillis();
        long keepTime = endTime - startTime;
        Log.d(this.getClass().getSimpleName(), String.valueOf(keepTime));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) ev.getRawX();
            int y = (int) ev.getRawY();
            //views其实应该是服务端下发的过滤选择的list
            for (int i = 0; i < views.size(); i++) {
                Rect rect = new Rect();
                views.get(i).getGlobalVisibleRect(rect);
                if (rect.contains(x, y)) {
                    Log.d("命中：", viewPath.get(i) + "这个是我们的数据上报埋点");
                    return super.dispatchTouchEvent(ev);
                }

            }
            //没能找到，则该view应该在fragment中
            if (dispatchTouchEventable != null) {
                dispatchTouchEventable.point(x, y);
            }

        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 获取子view的路径：
     * ----从rootView到子view的路径
     *
     * @param rootView   根view 由getWindow().getDecoreView.getRootView()获得
     * @param nodeVector 一个从根节点到叶子节点的路径
     */
    public void getViewPathWithTree(View rootView, LinkedList<String> nodeVector) {

        if (rootView != null) {
            nodeVector.add(rootView.getClass().getSimpleName());
        }
        if (!(rootView instanceof ViewGroup)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String str :
                    nodeVector) {
                stringBuilder.append(str).append("_");

            }
            viewPath.add(stringBuilder.toString());
            nodeVector.removeLast();
            return;
        } else {
            int childCount = ((ViewGroup) rootView).getChildCount();
            for (int i = 0; i < childCount; i++) {
                getViewPathWithTree(((ViewGroup) rootView).getChildAt(i), nodeVector);
            }
            nodeVector.removeLast();

        }

    }

    //给fragment的接口
    interface DispatchTouchEventable {
        /**
         * 因为fragment没有onIntercept方法，所以要
         * 通過view向fragment传输坐标
         *
         * @param x
         * @param y
         */
        void point(int x, int y);
    }


}

