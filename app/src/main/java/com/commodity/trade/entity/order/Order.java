package com.commodity.trade.entity.order;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 订单信息
 *  商品id
 * Created by AaFaa
 * on 2020/12/21
 * in package com.commodity.trade.entity.order
 * with project trade
 */
public class Order extends BmobObject {
    public String getShareId() {
        return ShareId;
    }

    public void setShareId(String shareId) {
        ShareId = shareId;
    }


    /**
     * Id
     */
    public String ShareId;
    /**
     * 买家Id
     */
    public String buyerId;
    /**
     * 商品名称
     */
    public String itemName;
    /**
     * 商品单价
     */
    public float unitPrice;
    /**
     * 商品数量
     */
    public int itemAmount;
    /**
     * 佣金
     */
    public float commission;
    /**
     * 合同文件
     */
    public String documentUrl;
    /**
     * 详细描述
     */
    public String detailDescription;

    @Override
    public String toString() {
        return "Order{" +
                "ShareId='" + ShareId + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", unitPrice=" + unitPrice +
                ", itemAmount=" + itemAmount +
                ", commission=" + commission +
                ", documentUrl='" + documentUrl + '\'' +
                ", detailDescription='" + detailDescription + '\'' +
                ", status=" + status +
                '}';
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean status;


    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

}
