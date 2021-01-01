package com.commodity.trade.entity.message_bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by AaFaa
 * on 2020/12/27
 * in package com.commodity.trade.entity.message_bean
 * with project trade
 * @author Aaron
 */
public class InviteMessageBox extends BmobObject {


    private String invitor;
    private String newUser;

    public String getInvitor() {
        return invitor;
    }

    public void setInvitor(String invitor) {
        this.invitor = invitor;
    }

    public String getNewUser() {
        return newUser;
    }

    public void setNewUser(String newUser) {
        this.newUser = newUser;
    }


    @Override
    public String toString() {
        return "InviteMessageBean{" +
                "invitor='" + invitor + '\'' +
                ", newUser='" + newUser + '\'' +
                '}';
    }


}
