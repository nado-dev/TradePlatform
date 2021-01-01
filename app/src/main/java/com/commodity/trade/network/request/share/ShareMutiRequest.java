package com.commodity.trade.network.request.share;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.commodity.trade.entity.share.ShareBeanMuti;
import com.commodity.trade.network.ApiEngine;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by AaFaa
 * on 2020/12/23
 * in package com.commodity.trade.network.request.share
 * with project trade
 */
public class ShareMutiRequest {
    MutableLiveData <ShareBeanMuti> shareData = new MutableLiveData <>();

    public MutableLiveData <ShareBeanMuti> getShareData() {
        return shareData;
    }

    public void getListByUid(String uid) {
        ApiEngine.getInstance().getApiService().findUidInfo(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer <ShareBeanMuti>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShareBeanMuti shareBeanMutis) {
                        shareData.postValue(shareBeanMutis);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(this.getClass().getSimpleName(), e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getListByShareId(String shareId) {
        ApiEngine.getInstance().getApiService().findShareIdInfo(shareId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer <ShareBeanMuti>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShareBeanMuti shareBeanMutis) {
                        shareData.postValue(shareBeanMutis);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(this.getClass().getSimpleName(), e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getChances(String uid) {
        ApiEngine.getInstance().getApiService().findChances(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer <ShareBeanMuti>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShareBeanMuti shareBeanMutis) {
                        shareData.postValue(shareBeanMutis);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(this.getClass().getSimpleName(), e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
