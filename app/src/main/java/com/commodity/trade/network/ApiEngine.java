package com.commodity.trade.network;


import com.commodity.mylibrary.util.Utils;
import com.commodity.trade.network.interceptor.NetWorkInterceptor;
import com.commodity.trade.network.interceptor.ResponseInterceptor;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 网络请求工具
 * @author Aaron
 */
public class ApiEngine {
    private volatile static ApiEngine apiEngine;
    private Retrofit retrofit;

    private ApiEngine() {

        //添加网络拦截器
        NetWorkInterceptor netWorkInterceptor = new NetWorkInterceptor();
        //解析返回结果的Interceptor
        ResponseInterceptor responseInterceptor = new ResponseInterceptor();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addNetworkInterceptor(netWorkInterceptor)
                .addInterceptor(responseInterceptor)
//                .cache(cache)
                .build();

        Gson gson = new Gson();
        retrofit = new Retrofit.Builder()
//                .baseUrl(ApiService.BASE_URL)
                .baseUrl(ApiService.TEST_MY_PC)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static ApiEngine getInstance() {
        if (apiEngine == null) {
            synchronized (ApiEngine.class) {
                if (apiEngine == null) {
                    apiEngine = new ApiEngine();
                }
            }
        }
        return apiEngine;
    }

    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }
}
