package com.commodity.trade.entity.demo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AaFaa
 * on 2020/12/17
 * in package com.commodity.trade.entity.demo
 * with project trade
 */
public class RegisterBean {

    /**
     * errorCode : 200
     * msg : 添加成功
     * data : {"mid":"0000000000","userType":-1,"name":"官方","inviteCode":"000000","fans":[{"mid":"asdfghjkl1","userType":0,"name":"3号用户","inviteCode":"asdfgh","fans":null}]}
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
         * mid : 0000000000
         * userType : -1
         * name : 官方
         * inviteCode : 000000
         * fans : [{"mid":"asdfghjkl1","userType":0,"name":"3号用户","inviteCode":"asdfgh","fans":null}]
         */

        @SerializedName("mid")
        private String mid;
        @SerializedName("userType")
        private int userType;
        @SerializedName("name")
        private String name;
        @SerializedName("inviteCode")
        private String inviteCode;
        @SerializedName("fans")
        private List <InviteBean> fans;

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
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

        public List <InviteBean> getFans() {
            return fans;
        }

        public void setFans(List <InviteBean> fans) {
            this.fans = fans;
        }
    }
}
