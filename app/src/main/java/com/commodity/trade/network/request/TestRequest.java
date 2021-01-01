package com.commodity.trade.network.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.commodity.trade.entity.demo.TestBean;
import com.commodity.trade.network.ApiEngine;

import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by AaFaa
 * on 2020/12/17
 * in package com.commodity.trade.network.request
 * with project trade
 * @author Aaron
 */
public class TestRequest {
    MutableLiveData <TestBean> testData = new MutableLiveData <>();

    public LiveData <TestBean> getTestData() {
        return testData;
    }

    public void test() {
        ApiEngine.getInstance().getApiService().testApi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer <TestBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TestBean testBean) {
                        testData.postValue(testBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(this.getClass().getSimpleName(), Objects.requireNonNull(e.getMessage()));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
