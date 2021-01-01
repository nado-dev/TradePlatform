package com.commodity.trade;


import android.app.Application;

import com.commodity.trade.util.Config;

import cn.bmob.v3.Bmob;

/**
 * Created by AaFaa
 * on 2020/12/11
 * in package com.commodity.trade
 * with project trade
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化Bmob
        Bmob.initialize(this, Config.BMOB_APPLICATION_ID);
    }
}
