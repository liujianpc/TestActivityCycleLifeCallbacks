package com.example.xp_pc_xxx.testactivitycyclelifecallbacks.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Date: 2017/11/16
 * Created by XP-PC-XXX
 */

public class ToastUtil {
    private static Toast mToast;

    public static void showToast(Context context, String message) {
        if (null == mToast) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }
}
