package com.zw.rule.surety;

/**
 * Created by zoukaixuan on 2017/12/14.
 * 担保人订单关联表实体类
 *
 */
public class SuretyRelOrder {
    private String id;//id
    private String suretyId;// 担保人id
    private String orderId ;//订单id
    private String state ;//状态 0-停用 1-启用
    private String createTime ;//创建时间
    private String alterTime ;//更改时间
    private String orderLimit ;//订单限额

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSuretyId() {
        return suretyId;
    }

    public void setSuretyId(String suretyId) {
        this.suretyId = suretyId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAlterTime() {
        return alterTime;
    }

    public void setAlterTime(String alterTime) {
        this.alterTime = alterTime;
    }

    public String getOrderLimit() {
        return orderLimit;
    }

    public void setOrderLimit(String orderLimit) {
        this.orderLimit = orderLimit;
    }

    public SuretyRelOrder() {
    }
    @Override
    public String toString() {
        return "SuretyRelOrder{" +
                "id='" + id + '\'' +
                ", suretyId='" + suretyId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", state='" + state + '\'' +
                ", createTime='" + createTime + '\'' +
                ", alterTime='" + alterTime + '\'' +
                ", orderLimit='" + orderLimit + '\'' +
                '}';
    }
}
