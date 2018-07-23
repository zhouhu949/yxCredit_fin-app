package com.zw.rule.customer.po;

import java.math.BigDecimal;
import java.util.Date;

public class BusinessRepayment {
    private String id;

    private String orderNo;

   // private String repaymentId;

    private String status;

    private Integer period;

    private Date interestStartTime;

    private Date repaymentTime;

    private Date repaymentYesTime;

    private BigDecimal repaymentAccount;

    private BigDecimal repaymentYesAccount;

    private BigDecimal capital;

    private BigDecimal yesCapital;

    //private BigDecimal rate;

    private BigDecimal interest;

    private BigDecimal repaymentYesInterest;

    private Integer isRepayment;

    private Integer repaymentType;

    private Integer lateDays;

    //private BigDecimal lateRate;

    private BigDecimal lateInterest;

    //private BigDecimal derateAmount;

    //private Integer periods;

    //private String channelType;

    //private String remark;

    private Date createTime;

    //private String createUserId;

    //private String updateUserId;

    private Date updateTime;

    //private Integer isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Date getInterestStartTime() {
        return interestStartTime;
    }

    public void setInterestStartTime(Date interestStartTime) {
        this.interestStartTime = interestStartTime;
    }

    public Date getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(Date repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public Date getRepaymentYesTime() {
        return repaymentYesTime;
    }

    public void setRepaymentYesTime(Date repaymentYesTime) {
        this.repaymentYesTime = repaymentYesTime;
    }

    public BigDecimal getRepaymentAccount() {
        return repaymentAccount;
    }

    public void setRepaymentAccount(BigDecimal repaymentAccount) {
        this.repaymentAccount = repaymentAccount;
    }

    public BigDecimal getRepaymentYesAccount() {
        return repaymentYesAccount;
    }

    public void setRepaymentYesAccount(BigDecimal repaymentYesAccount) {
        this.repaymentYesAccount = repaymentYesAccount;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getYesCapital() {
        return yesCapital;
    }

    public void setYesCapital(BigDecimal yesCapital) {
        this.yesCapital = yesCapital;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getRepaymentYesInterest() {
        return repaymentYesInterest;
    }

    public void setRepaymentYesInterest(BigDecimal repaymentYesInterest) {
        this.repaymentYesInterest = repaymentYesInterest;
    }

    public Integer getIsRepayment() {
        return isRepayment;
    }

    public void setIsRepayment(Integer isRepayment) {
        this.isRepayment = isRepayment;
    }

    public Integer getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(Integer repaymentType) {
        this.repaymentType = repaymentType;
    }

    public Integer getLateDays() {
        return lateDays;
    }

    public void setLateDays(Integer lateDays) {
        this.lateDays = lateDays;
    }

    public BigDecimal getLateInterest() {
        return lateInterest;
    }

    public void setLateInterest(BigDecimal lateInterest) {
        this.lateInterest = lateInterest;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /*public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    public BigDecimal getDerateAmount() {
        return derateAmount;
    }

    public void setDerateAmount(BigDecimal derateAmount) {
        this.derateAmount = derateAmount;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType == null ? null : channelType.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getLateRate() {
        return lateRate;
    }

    public void setLateRate(BigDecimal lateRate) {
        this.lateRate = lateRate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getRepaymentId() {
        return repaymentId;
    }

    public void setRepaymentId(String repaymentId) {
        this.repaymentId = repaymentId == null ? null : repaymentId.trim();
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }*/
}