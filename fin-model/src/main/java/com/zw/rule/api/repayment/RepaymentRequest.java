package com.zw.rule.api.repayment;

import java.io.Serializable;

/**
 * 放款信息实体
 * @author 陈淸玉 2018-07-20
 */
public class RepaymentRequest implements Serializable {
    private static final long serialVersionUID = -5080615095903592456L;
    private String orderId;
    private String customerId;

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
                '}';
    }
}
