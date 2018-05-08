package com.zw.rule.officeClerkEntity;

/**
 * Created by zoukaixuan on 2017/12/18.
 * 办单员和商户关联的实体类
 */
public class MerchantSalemanRel {
    private String id;
    private String employeeId;//办单员id
    private String merchantId;//商户id
    private String state;//状态

    public MerchantSalemanRel() {
    }

    public MerchantSalemanRel(String id, String employeeId, String merchantId, String state) {
        this.id = id;
        this.employeeId = employeeId;
        this.merchantId = merchantId;
        this.state = state;
    }

    @Override
    public String toString() {
        return "MerchantSalemanRel{" +
                "id='" + id + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
