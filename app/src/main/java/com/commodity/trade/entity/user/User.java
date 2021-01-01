package com.commodity.trade.entity.user;

import cn.bmob.v3.BmobUser;

/**
 * Created by AaFaa
 * on 2020/12/11
 * in package com.commodity.trade.entity.user
 * with project trade
 */
public class User extends BmobUser {

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int userType;

    public String getInvitorCode() {
        return invitorCode;
    }

    public void setInvitorCode(String invitorCode) {
        this.invitorCode = invitorCode;
    }

    public String invitorCode;

    @Override
    public String toString() {
        return "User{" +
                "userType=" + userType +
                ", invitorCode='" + invitorCode + '\'' +
                '}';
    }
    public String convertTypeToString(int type) {
        switch (type) {
            case UserConfig.USER_TYPE_SELLER:
                return "卖家";
            case UserConfig.USER_TYPE_MIDDLE:
                return "中间人";
            case UserConfig.USER_TYPE_BUYER:
                return "买家";
            default:
        }
        return "";
    }
}
