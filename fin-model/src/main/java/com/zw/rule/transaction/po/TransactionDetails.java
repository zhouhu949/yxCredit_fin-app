package com.zw.rule.transaction.po;

/**
 * Created by Administrator on 2017/12/9.
 */
public class TransactionDetails {
    private  String id;
    private  String batchNo;
    private  String amount;
    private  String repaymentId;
    private  String orderId;
    private  String baofuCode;
    private  String baofuMag;
    private  String type;
    private  String state;
    private  String creatTime;
    private  String successTime;
    private  String baofuAsynchronousMag;
    private  String baofuAsynchronousCode;
    private  String bankName;
    private  String bankCard;
    private  String source; //0黄金袋，1商品贷

    //放款形式 0-线下 1-线上
    private String fangkuanType;
    //交易描述
    private String describe;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBankName() {
        return bankName;
    }

    public String getFangkuanType() {
        return fangkuanType;
    }

    public void setFangkuanType(String fangkuanType) {
        this.fangkuanType = fangkuanType;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBaofuAsynchronousMag() {
        return baofuAsynchronousMag;
    }

    public void setBaofuAsynchronousMag(String baofuAsynchronousMag) {
        this.baofuAsynchronousMag = baofuAsynchronousMag;
    }

    public String getBaofuAsynchronousCode() {
        return baofuAsynchronousCode;
    }

    public void setBaofuAsynchronousCode(String baofuAsynchronousCode) {
        this.baofuAsynchronousCode = baofuAsynchronousCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRepaymentId() {
        return repaymentId;
    }

    public void setRepaymentId(String repaymentId) {
        this.repaymentId = repaymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBaofuCode() {
        return baofuCode;
    }

    public void setBaofuCode(String baofuCode) {
        this.baofuCode = baofuCode;
    }

    public String getBaofuMag() {
        return baofuMag;
    }

    public void setBaofuMag(String baofuMag) {
        this.baofuMag = baofuMag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(String successTime) {
        this.successTime = successTime;
    }
}
