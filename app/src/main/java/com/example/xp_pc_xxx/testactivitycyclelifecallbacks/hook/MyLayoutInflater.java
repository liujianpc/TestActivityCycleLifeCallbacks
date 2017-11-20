package com.example.xp_pc_xxx.testactivitycyclelifecallbacks.hook;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Date: 2017/11/16
 * Created by XP-PC-XXX
 */

class MyLayoutInflater extends LayoutInflater {
    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.webkit."
    };
    private static int VIEW_TAG = 0x10000000;
    private static String SYSTEM_VIEW_PREFIX_1 = "0x01";
    private static String SYSTEM_VIEW_PREFIX_2 = "0x00";
    private static boolean isFirst = true;

    protected MyLayoutInflater(Context context) {
        super(context);
    }

    protected MyLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new MyLayoutInflater(this, newContext);
    }

    /**
     * 参照PhoneLayoutInflater
     *
     * @param name
     * @param attrs
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
        for (String prefix : sClassPrefixList) {
            try {
                View view = createView(name, prefix, attrs);
                if (view != null) {
                    return view;
                }
            } catch (ClassNotFoundException e) {
                // In this case we want to let the base class take a crack
                // at it.
            }
        }
        return super.onCreateView(name, attrs);
    }

    @Override
    public View inflate(int resource, @Nullable ViewGroup root, boolean attachToRoot) {
        View viewGroup = super.inflate(resource, root, attachToRoot);
        View rootView = viewGroup;
        View tempView = viewGroup;
        /*while (tempView != null) {
            rootView = viewGroup;
            tempView = (ViewGroup) tempView.getParent();
        }*/

        //rootView = getActivity(rootView).getWindow().getDecorView().getRootView();
        visitAllViewGroup(rootView);
        return viewGroup;
    }

    /**
     * 第二套方案：
     * 采用BFS模式遍历viewtree
     * 由唯一性的viewTree路径来构建viewId
     *
     * @param rootView
     * @param
     */
    private static void visitAllViewGroup(View rootView) {

        if (rootView != null && rootView instanceof ViewGroup) {
            Queue<View> queue = new ArrayDeque<>();
            queue.add(rootView);
            Map<String, List<View>> innerMap = new HashMap<>();
            Map<ViewGroup, Map<String, List<View>>> map = new HashMap<>();

            while (queue.size() != 0) {
                View view = queue.poll();
                /*if (view.getParent() != null) {
                    ViewGroup viewGroup = (ViewGroup) view.getParent();
*/
                if (isFirst) {
                    if (view.getTag() == null) {

                        view.setTag(getActivityName(view) + "/" + view.getClass().getSimpleName());
                    }
                    isFirst = false;
                } //else {
                //父view不为空，由父view获得childMap
                if (view.getParent() != null) {
                    ViewGroup viewGroup = (ViewGroup) view.getParent();
                    if (map.containsKey(viewGroup)) {
                        //拿到childMap
                        Map<String, List<View>> childMap = map.get(viewGroup);
                        List<View> childViews = childMap.get(view.getClass().getSimpleName());
                        int index = childViews.indexOf(view);
                        if (view.getTag() == null) {
                            view.setTag(((ViewGroup) (view.getParent())).getTag() + "/" + view.getClass().getSimpleName() + "[" + index + "]");
                        }

                    }
                    //}
                }
                if (view instanceof ViewGroup) {
                    ViewGroup viewGroup_inner = (ViewGroup) view;
                    for (int i = 0; i < viewGroup_inner.getChildCount(); i++) {
                        queue.add(viewGroup_inner.getChildAt(i));
                        View view_inner = viewGroup_inner.getChildAt(i);

                        //如果innerMap包含了这个类型的view,则直接取list，在往中添加即可
                        if (innerMap.containsKey(view_inner.getClass().getSimpleName())) {
                            innerMap.get(view_inner.getClass().getSimpleName()).add(view_inner);
                        } else {
                            //如果不存在，则新建list
                            List<View> views = new ArrayList<>();
                            views.add(view_inner);
                            innerMap.put(view_inner.getClass().getSimpleName(), views);
                        }

                    }
                    map.put(viewGroup_inner, innerMap);

                }

            }
            //回复isFirst值为true，否则只有首页viewTree构建成功，其他页面的viewtree不会有page
            isFirst = true;
        }

    }
    /**
     *
     * 遍历viewGroup给每一个view都打上viewId标签
     * viewId = page + viewTree
     * 其中page是view所属的页面
     * viewTree是viewGroup0/viewGroup1/viewGroupN/TextView[2]
     *
     * @param rootView
     */
    /*private void visitAllViewGroup(View rootView, boolean isFirst) {
        if (rootView != null && rootView instanceof ViewGroup) {
            // String idStr = String.valueOf(rootView.getId());
            //如果是系统的view，则要考虑他的碎片化问题
            *//*if (isSystemView(idStr)) {
                if (rootView.getTag() == null) {
                    if (!isFirst){
                        rootView.setTag("");
                    }else {
                        //rootView.setTag(getContext().toString()+"");
                    }

                }
            } else {*//*
            if (rootView.getTag() == null) {
                if (!isFirst) {
                    rootView.setTag(rootView.getClass().getSimpleName());
                } else {
                    rootView.setTag(getActivityName(rootView) + "/" + rootView.getClass().getSimpleName());
                }
            }
            //}

            ViewGroup viewGroup = (ViewGroup) rootView;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < ; i++) {

            }
            for (int i = 0; i < childCount; i++) {
                View childView = viewGroup.getChildAt(i);
                String childViewIdStr = String.valueOf(childView.getId());

                if (isSystemView(childViewIdStr)) {
                    childView.setTag("");
                } else {
                    childView.setTag(viewGroup.getTag().toString() + "/" + childView.getClass().getSimpleName());
                }

                if (childView instanceof ViewGroup) {
                    visitAllViewGroup(childView, false);
                }
            }
        }
    }*/


    /**
     * 第一套方案
     * 采用DFS遍历
     * 使用MD5值作为view的id
     * 没访问一个view，该view的id因子自增求MD5,再与之前的父view的id的md5值拼接，又求一次md5
     * 保证了id的唯一性
     *
     * @param viewTag
     * @param s
     * @return
     */
   /*private void visitAllViewGroup(View rootView) {
        if (rootView != null && rootView instanceof ViewGroup) {
            if (rootView.getTag() == null) {
                rootView.setTag(getViewTag());
            }
            ViewGroup viewGroup = (ViewGroup) rootView;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = viewGroup.getChildAt(i);
                if (childView.getTag() == null) {
                    childView.setTag(combineTag(getViewTag(), viewGroup.getTag().toString()));
                }
                Log.e("Hooker", "childView name = " + childView.getClass().getName() + "id = " + childView.getTag().toString());
                if (childView instanceof ViewGroup) {
                    visitAllViewGroup(childView);
                }
            }
        }
    }
*/
    private static String combineTag(String viewTag, String s) {
        return getMD5(getMD5(viewTag) + getMD5(s));
    }


    private static String getViewTag() {
        return String.valueOf(VIEW_TAG++);

    }

    private static String getMD5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            return new BigInteger(1, md5.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean isSystemView(String idStr) {
        return idStr.startsWith(SYSTEM_VIEW_PREFIX_1) || idStr.startsWith(SYSTEM_VIEW_PREFIX_2);
    }

    public static String getActivityName(View view) {
        Context context = view.getContext();
        if (context instanceof Activity) {
            //context本身是Activity的实例
            return context.getClass().getSimpleName();
        } else if (context instanceof ContextWrapper) {
            //Activity有可能被系统＂装饰模式＂，看看context.base是不是Activity
            Activity activity = getActivityFromContextWrapper((ContextWrapper) context);
            if (activity != null) {
                return activity.getClass().getSimpleName();
            } else {
                //如果从view.getContext()拿不到Activity的信息（比如view的context是Application）,则返回当前栈顶Activity的名字
                return getTopActivity(context);
            }
        }
        return "";
    }

    /**
     * @param view
     * @return
     */
    public static Activity getActivity(View view) {
        Context context = view.getContext();
        if (context instanceof Activity) {
            //context本身是Activity的实例
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            //Activity有可能被系统＂装饰模式＂，看看context.base是不是Activity
            Activity activity = getActivityFromContextWrapper((ContextWrapper) context);
            if (activity != null) {
                return activity;
            } else {
                //如果从view.getContext()拿不到Activity的信息（比如view的context是Application）,则返回当前栈顶Activity的名字
                return null;
            }
        }
        return null;
    }

    private static Activity getActivityFromContextWrapper(ContextWrapper context) {
        if (context.getBaseContext() instanceof Activity) {
            return (Activity) context.getBaseContext();
        } else {
            return null;
        }
    }

    public static String getTopActivity(Context context) {
        android.app.ActivityManager manager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null) {
            return (runningTaskInfos.get(0).topActivity).getClass().getSimpleName();
        } else {
            return "";
        }
    }

}
