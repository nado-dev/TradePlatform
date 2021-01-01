package com.commodity.trade.network.request;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.commodity.trade.entity.demo.InviteBean;
import com.commodity.trade.network.ApiEngine;
import com.commodity.trade.network.ApiService;

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
public class InviteRequest {
    MutableLiveData<InviteBean> inviteData = new MutableLiveData <>();

    public MutableLiveData <InviteBean> getInviteData() {
        return inviteData;
    }

    public void getInvited(String inviteCode, String mid, String uName, int uType) {
        ApiEngine.getInstance().getApiService().getInvited(inviteCode, mid, uName, uType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer <InviteBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(InviteBean inviteBean) {
                        inviteData.postValue(inviteBean);
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
