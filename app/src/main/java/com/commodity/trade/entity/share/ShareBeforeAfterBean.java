package com.commodity.trade.entity.share;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AaFaa
 * on 2020/12/26
 * in package com.commodity.trade.entity.share
 * with project trade
 */
public class ShareBeforeAfterBean {

    /**
     * errorCode : 230
     * msg : 查询成功
     * data : {"before":0,"after":1}
     */

    @SerializedName("errorCode")
    private int errorCode;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * before : 0
         * after : 1
         */

        @SerializedName("before")
        private int before;
        @SerializedName("after")
        private int after;

        public int getBefore() {
            return before;
        }

        public void setBefore(int before) {
            this.before = before;
        }

        public int getAfter() {
            return after;
        }

        public void setAfter(int after) {
            this.after = after;
        }
    }
}
