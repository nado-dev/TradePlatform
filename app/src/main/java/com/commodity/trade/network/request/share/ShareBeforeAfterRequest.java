package com.commodity.trade.network.request.share;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.commodity.trade.entity.share.ShareBeforeAfterBean;
import com.commodity.trade.entity.share.ShareIntegerBean;
import com.commodity.trade.network.ApiEngine;

import java.util.stream.Stream;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by AaFaa
 * on 2020/12/26
 * in package com.commodity.trade.network.request.share
 * with project trade
 */
public class ShareBeforeAfterRequest {
    MutableLiveData <ShareBeforeAfterBean> shareData = new MutableLiveData <>();

    public MutableLiveData <ShareBeforeAfterBean> getShareData() {
        return shareData;
    }

    public void findBeforeAfter(String shareId, String uid) {
        ApiEngine.getInstance().getApiService().findBeforeAfter(shareId, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer <ShareBeforeAfterBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShareBeforeAfterBean shareBeforeAfterBean) {
                        shareData.postValue(shareBeforeAfterBean);
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
