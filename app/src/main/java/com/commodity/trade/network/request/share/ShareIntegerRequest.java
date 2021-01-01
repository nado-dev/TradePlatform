package com.commodity.trade.network.request.share;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.commodity.trade.entity.share.ShareBean;
import com.commodity.trade.entity.share.ShareIntegerBean;
import com.commodity.trade.network.ApiEngine;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by AaFaa
 * on 2020/12/24
 * in package com.commodity.trade.network.request.share
 * with project trade
 */
public class ShareIntegerRequest {
    MutableLiveData <ShareIntegerBean> shareData = new MutableLiveData <>();

    public MutableLiveData <ShareIntegerBean> getShareData() {
        return shareData;
    }

    public void findFinishCount(String uid) {
        ApiEngine.getInstance().getApiService().findFinishCount(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer <ShareIntegerBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShareIntegerBean shareBean) {
                        shareData.postValue(shareBean);
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

    public void findTotalCount(String uid) {
        ApiEngine.getInstance().getApiService().findTotalCount(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer <ShareIntegerBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShareIntegerBean shareBean) {
                        shareData.postValue(shareBean);
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
