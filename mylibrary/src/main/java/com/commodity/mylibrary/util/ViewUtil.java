package com.commodity.mylibrary.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AaFaa
 * on 2020/12/12
 * in package com.commodity.mylibrary.util
 * with project trade
 */
public class ViewUtil {
    public static int getViewDepth(View view) {
        if( !(view instanceof ViewGroup)) {
            return 0;
        }
        if (((ViewGroup) view).getChildCount() == 0) {
            return 0;
        }

        int ans = 0;
        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
            ans = Math.max(ans, getViewDepth(((ViewGroup) view).getChildAt(i)));
        }
        return ans + 1;
    }

    public static List<View> getAllSubView(View view) {
        List<View> ans = new ArrayList <>();
        if( !(view instanceof ViewGroup)) {
            return ans;
        }
        if (((ViewGroup) view).getChildCount() == 0) {
            return ans;
        }
        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
            View subView = ((ViewGroup) view).getChildAt(i);
            ans.add(subView);
            List <View> allSubView = getAllSubView(subView);
            ans.addAll(allSubView);
        }
        return ans;
    }

    /**
     * 将一个View转为Bitmap
     * @param v 需要转化的View
     * @return 目标View转化的Bitmap
     */
    public static Bitmap getBitmapByView(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
}
