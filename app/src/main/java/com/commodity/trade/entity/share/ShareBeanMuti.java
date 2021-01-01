package com.commodity.trade.entity.share;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AaFaa
 * on 2020/12/23
 * in package com.commodity.trade.entity.share
 * with project trade
 */
public class ShareBeanMuti {

    /**
     * errorCode : 202
     * msg : 查询成功
     * data : [{"shareId":"000002","userType":0,"name":"2user","inviteCode":"000000","fans":null},{"shareId":"000002","userType":1,"name":"3user","inviteCode":"000kla","fans":null}]
     */

    @SerializedName("errorCode")
    private int errorCode;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private List <DataBean> data;

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

    public List <DataBean> getData() {
        return data;
    }

    public void setData(List <DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * shareId : 000002
         * userType : 0
         * name : 2user
         * inviteCode : 000000
         * fans : null
         */

        @SerializedName("shareId")
        private String shareId;
        @SerializedName("userType")
        private int userType;
        @SerializedName("name")
        private String name;
        @SerializedName("inviteCode")
        private String inviteCode;
        @SerializedName("fans")
        private Object fans;

        public String getShareId() {
            return shareId;
        }

        public void setShareId(String shareId) {
            this.shareId = shareId;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public Object getFans() {
            return fans;
        }

        public void setFans(Object fans) {
            this.fans = fans;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "shareId='" + shareId + '\'' +
                    ", userType=" + userType +
                    ", name='" + name + '\'' +
                    ", inviteCode='" + inviteCode + '\'' +
                    ", fans=" + fans +
                    '}';
        }
    }
}
