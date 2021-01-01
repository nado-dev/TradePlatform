package com.commodity.trade.network.request.share;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.commodity.trade.entity.share.ShareBean;
import com.commodity.trade.network.ApiEngine;

import cn.bmob.v3.http.bean.Api;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by AaFaa
 * on 2020/12/22
 * in package com.commodity.trade.network.request
 * with project trade
 * @author Aaron
 */
public class ShareRequest {
    MutableLiveData <ShareBean> shareData = new MutableLiveData <>();

    public MutableLiveData <ShareBean> getShareData() {
        return shareData;
    }

    public void getShared(String inviteCode, String sharedId, String userName,
                          String userId, int userType) {
        ApiEngine.getInstance().getApiService().getShared(inviteCode, sharedId, userName,
                userId, userType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer <ShareBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShareBean shareBean) {
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

    public void findShareCodeInfo(String shareCode) {
        ApiEngine.getInstance().getApiService().findInviteCodeInfo(shareCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer <ShareBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShareBean shareBean) {
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
