package com.commodity.mylibrary.util;

import android.content.Context;

/**
 * 显示单位转换工具
 */
public class SizeUtil {
    public static int dp2px(Context context, float dp){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale +0.5f);
    }
}
