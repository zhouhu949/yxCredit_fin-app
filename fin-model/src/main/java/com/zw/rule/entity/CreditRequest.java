package com.zw.rule.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/23 0023.
 */
public class CreditRequest extends RECommonRequest implements Serializable {
    private static final long serialVersionUID = -6998736127906395025L;

    private String id;

    /**
     * 是否同步调用
     */
    private boolean sync = true;

    /**
     * 引擎Id
     */
    private Long engineId;

    /**
     * 引擎主版本,reqtype=2的时候才需赋值
     */
    private Integer version;

    /**
     * 引擎子版本,默认传0,reqtype=2的时候才需赋值
     */
    private Integer subversion;

    /**
     * 1:获取当前引擎正在运行版本,2:获取当前引擎中对应的版本
     */
    //@ApiModelProperty(value = "引擎版本，1:获取当前引擎正在运行版本,2:获取当前引擎中对应的版本，默认值：1", dataType = "Integer", required = true)
    private Integer reqtype = 1;

    /**
     * 产品系列名称
     */
    private String productSeriesName;

    /**
     * 产品ID
     */
    private String productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 进件编号
     */
    private String applicationId;

    /**
     * 应用名
     */
    private String appName;

    /**
     * 详细进件参数
     */
    private CreditRequestParam creditRequestParam;

    /**
     * 状态（不需要输入）：1：未处理，2：已处理，3：异常
     */
    private String status = "1";

    /**
     * 错误信息（不需要输入）
     */
    private String errorMsg;

    /**
     * 创建时间（不需要输入）
     */
    private Date createDate = new Date();

    /**
     * 更新时间（不需要输入）
     */
    private Date updateDate;

    /**
     * 执行间隔（不需要输入），表示多久之后可以处理该条记录
     */
    private Long interval;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public CreditRequestParam getCreditRequestParam() {
        return creditRequestParam;
    }

    public void setCreditRequestParam(CreditRequestParam creditRequestParam) {
        this.creditRequestParam = creditRequestParam;
    }

    public String getStatus() {
        return status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getEngineId() {
        return engineId;
    }

    public void setEngineId(Long engineId) {
        this.engineId = engineId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getSubversion() {
        return subversion;
    }

    public void setSubversion(Integer subversion) {
        this.subversion = subversion;
    }

    public Integer getReqtype() {
        return reqtype;
    }

    public void setReqtype(Integer reqtype) {
        this.reqtype = reqtype;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSeriesName() {
        return productSeriesName;
    }

    public void setProductSeriesName(String productSeriesName) {
        this.productSeriesName = productSeriesName;
    }

    @Override
    public String toString() {
        return "CreditRequest [id=" + id + ", sync=" + sync + ", engineId="
                + engineId + ", version=" + version + ", subversion="
                + subversion + ", reqtype=" + reqtype + ", productSeriesName="
                + productSeriesName + ", productId=" + productId
                + ", productName=" + productName + ", applicationId="
                + applicationId + ", appName=" + appName
                + ", creditRequestParam=" + creditRequestParam + ", status="
                + status + ", errorMsg=" + errorMsg + ", createDate="
                + createDate + ", updateDate=" + updateDate + ", interval="
                + interval + "]";
    }

}
