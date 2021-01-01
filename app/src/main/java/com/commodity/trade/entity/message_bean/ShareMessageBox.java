package com.commodity.trade.entity.message_bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by AaFaa
 * on 2020/12/27
 * in package com.commodity.trade.entity.message_bean
 * with project trade
 */
public class ShareMessageBox extends BmobObject {
    private boolean isFinish;
    private String shareId;
    private String shareTo;
    private String shareFrom;

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getShareTo() {
        return shareTo;
    }

    public void setShareTo(String shareTo) {
        this.shareTo = shareTo;
    }

    public String getShareFrom() {
        return shareFrom;
    }

    public void setShareFrom(String shareFrom) {
        this.shareFrom = shareFrom;
    }


    @Override
    public String toString() {
        return "ShareMessageBox{" +
                "isFinish=" + isFinish +
                ", shareId='" + shareId + '\'' +
                ", shareTo='" + shareTo + '\'' +
                ", shareFrom='" + shareFrom + '\'' +
                '}';
    }
}
