package com.commodity.mylibrary.util;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by AaFaa
 * on 2020/12/15
 * in package com.commodity.trade.util
 * with project trade
 * @author Aaron
 */
public class ShareUtil {
    public static void shareTextToOuterApp(Activity activity, String msg ){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        activity.startActivity(Intent.createChooser(intent, "title"));
    }
}
