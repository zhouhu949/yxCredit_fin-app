package com.zw.rule.reportManage;

/**
 * Created by zoukaixuan on 2018/3/5.
 * 销售订单实体类
 */
public class Sorderdetail {
    private String orderId;
    private String province;//省 一级渠道
    private String city;//市 二级渠道
    private String district;//区 三级渠道
    private String merchantName;//商户名称
    private String orderNo;//订单编号
    private String customerName;//姓名
    private String customerTel;//手机号
    private String card;//身份证号
    private String merchantdiseName;//商品名称
    private String predictPrice;//首付金额
    private String salesmanName;//办单员
    private String salesmanTel;//办单员手机号
    private String yhze;//应还总额
    private String orderCapital;//订单本金
    private String yufukuan;//预付款
    private String payCount;//期数
    private String sureOrderTime;//确认订单时间               （暂时不知道是哪个字段 没有该字段）
    private String orderState;//订单状态
    private String yufukuanState;//预付款状态
    private String deliveryState;//发货状态 默认 0 已提货1, 发货2， 确认收货3
    private String loanState;//放款状态
    private String  jsState;//结算状态  (根据订单状态来查，如果状态不是结清状态其他的都是未结清状态)
    private String fahuoTime;//发货时间
    private String monthRate;//月息
    private String dayForDueDate;//离还款日天数               （单独拿出来查询）
    private String businessType;//行业类型

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getMerchantdiseName() {
        return merchantdiseName;
    }

    public void setMerchantdiseName(String merchantdiseName) {
        this.merchantdiseName = merchantdiseName;
    }

    public String getPredictPrice() {
        return predictPrice;
    }

    public void setPredictPrice(String predictPrice) {
        this.predictPrice = predictPrice;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getSalesmanTel() {
        return salesmanTel;
    }

    public void setSalesmanTel(String salesmanTel) {
        this.salesmanTel = salesmanTel;
    }

    public String getYhze() {
        return yhze;
    }

    public void setYhze(String yhze) {
        this.yhze = yhze;
    }

    public String getOrderCapital() {
        return orderCapital;
    }

    public void setOrderCapital(String orderCapital) {
        this.orderCapital = orderCapital;
    }

    public String getYufukuan() {
        return yufukuan;
    }

    public void setYufukuan(String yufukuan) {
        this.yufukuan = yufukuan;
    }

    public String getPayCount() {
        return payCount;
    }

    public void setPayCount(String payCount) {
        this.payCount = payCount;
    }

    public String getSureOrderTime() {
        return sureOrderTime;
    }

    public void setSureOrderTime(String sureOrderTime) {
        this.sureOrderTime = sureOrderTime;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getYufukuanState() {
        return yufukuanState;
    }

    public void setYufukuanState(String yufukuanState) {
        this.yufukuanState = yufukuanState;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public String getLoanState() {
        return loanState;
    }

    public void setLoanState(String loanState) {
        this.loanState = loanState;
    }

    public String getJsState() {
        return jsState;
    }

    public void setJsState(String jsState) {
        this.jsState = jsState;
    }

    public String getFahuoTime() {
        return fahuoTime;
    }

    public void setFahuoTime(String fahuoTime) {
        this.fahuoTime = fahuoTime;
    }

    public String getMonthRate() {
        return monthRate;
    }

    public void setMonthRate(String monthRate) {
        this.monthRate = monthRate;
    }

    public String getDayForDueDate() {
        return dayForDueDate;
    }

    public void setDayForDueDate(String dayForDueDate) {
        this.dayForDueDate = dayForDueDate;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
}
