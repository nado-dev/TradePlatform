package com.commodity.trade.entity.demo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AaFaa
 * on 2020/12/17
 * in package com.commodity.trade.entity.demo
 * with project trade
 */
public class TestBean {

    /**
     * errorCode : 200
     * msg : 查询成功
     * data : null
     */

    @SerializedName("errorCode")
    private int errorCode;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "errorCode=" + errorCode +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
