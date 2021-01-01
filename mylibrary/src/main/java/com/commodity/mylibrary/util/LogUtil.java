package com.commodity.mylibrary.util;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;

/**
 * Created by AaFaa
 * on 2020/12/17
 * in package com.commodity.mylibrary.util
 * with project trade
 */
public class LogUtil {
    public static void e(Activity context, String msg) {
        Log.e(context.getClass().getSimpleName(), msg);
    }

    public static void e(Fragment context, String msg) {
        Log.e(context.getClass().getSimpleName(), msg);
    }

    public static void e(Service context, String msg) {
        Log.e(context.getClass().getSimpleName(), msg);
    }
}
