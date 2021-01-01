package com.commodity.trade.entity.user;

/**
 * Created by AaFaa
 * on 2020/12/10
 * in package com.commodity.trade.entity
 * with project trade
 */
public class UserConfig {
    public static final int USER_TYPE_SELLER = 0;
    public static final int USER_TYPE_MIDDLE = 1;
    public static final int USER_TYPE_BUYER = 2;

    public long uid;

    public enum UserType {
        /**
         * 卖家
         */
        SELLER(0),
        /**
         * 中介
         */
        AGENT(1),
        /**
         * 买家
         */
        BUYER(2);

        private int type ;

        // 构造函数，枚举类型只能为私有

        private UserType(int nCode) {
            this.type = nCode;

        }

        @Override

        public String toString() {
            return String.valueOf ( this.type );
        }
    }

}
