package com.zw.rule.api.asset;

import java.io.Serializable;

/**
 * 资产同步
 * @author 仙海峰
 */
public class AssetRequest implements Serializable {
    private static final long serialVersionUID = -5080615095903592456L;
    private String orderId;
    private String customerId;
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "AssetRequest{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                '}';
    }
}
