package com.commodity.mylibrary.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

/**
 * Created by AaFaa
 * on 2020/12/17
 * in package com.commodity.mylibrary.util
 * with project trade
 */
public class ToastUtil {
    static void shortToast(Activity context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    static void shortToast(Fragment context, String msg) {
        Toast.makeText(context.getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    static void longToast(Activity context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    static void longToast(Fragment context, String msg) {
        Toast.makeText(context.getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
