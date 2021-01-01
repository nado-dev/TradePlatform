package com.commodity.trade.entity.feedback;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by AaFaa
 * on 2020/12/21
 * in package com.commodity.trade.entity.feedback
 * with project trade
 */
public class Feedback extends BmobObject {
    public String name;
    public String email;
    public String detail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }

}
