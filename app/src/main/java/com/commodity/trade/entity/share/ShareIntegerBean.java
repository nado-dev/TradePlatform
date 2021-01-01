package com.commodity.trade.entity.share;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AaFaa
 * on 2020/12/24
 * in package com.commodity.trade.entity.share
 * with project trade
 */
public class ShareIntegerBean {

    @Override
    public String toString() {
        return "ShareIntegerBean{" +
                "errorCode=" + errorCode +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * errorCode : 200
     * msg : 查询成功
     * data : 1
     */

    @SerializedName("errorCode")
    private int errorCode;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private int data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
