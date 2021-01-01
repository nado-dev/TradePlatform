package com.commodity.trade.entity.share;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AaFaa
 * on 2020/12/22
 * in package com.commodity.trade.entity.share
 * with project trade
 * @author Aaron
 */
public class ShareBean {

    /**
     * errorCode : 200
     * msg : 添加成功
     * data : {"shareId":"000002","userType":0,"name":"2user","inviteCode":"000000","fans":null}
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
    }
}
