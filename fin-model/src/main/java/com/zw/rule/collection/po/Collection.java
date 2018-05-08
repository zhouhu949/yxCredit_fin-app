package com.zw.rule.collection.po;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@催收记录@<br>
 * <strong>Create on : 2017年11月2日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Administrator <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public class Collection {
    private String id;//主键
    private String loanId;//借款id
    private String customerId;//客户id
    private String entranceExaminationDate;//进入电审时间
    private String electricExaminationPersonnel;//电审人
    private String electronicExaminationState;//电审状态 1电审中；2完成电审；
    private String electricExaminationComplete;//电审完成时间
    private String externalAuditorDate;//进入外审时间
    private String externalAuditorPersonnel;//外审人员
    private String externalAuditorState;//外审状态 1外审中；2外审完成；
    private String externalAuditorComplete;//外审完成时间
    private String entrustDate;//委外时间
    private String entrustState;//委外状态 0委外；1委外进行中；2委外完成；
    private String entrustComplete;//委外完成时间
    private String entrustSign;//委外管理状态 0：未标记；1：已标记（委外催收中）；2：委外完成

    public String getEntrustSign() {
        return entrustSign;
    }

    public void setEntrustSign(String entrustSign) {
        this.entrustSign = entrustSign;
    }

    public String getDerateId() {
        return derateId;
    }

    public void setDerateId(String derateId) {
        this.derateId = derateId;
    }

    private String derateId;//减免id

    private String state;//催收记录状态 0未完成；1完成

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getEntranceExaminationDate() {
        return entranceExaminationDate;
    }

    public void setEntranceExaminationDate(String entranceExaminationDate) {
        this.entranceExaminationDate = entranceExaminationDate;
    }

    public String getElectricExaminationPersonnel() {
        return electricExaminationPersonnel;
    }

    public void setElectricExaminationPersonnel(String electricExaminationPersonnel) {
        this.electricExaminationPersonnel = electricExaminationPersonnel;
    }

    public String getElectronicExaminationState() {
        return electronicExaminationState;
    }

    public void setElectronicExaminationState(String electronicExaminationState) {
        this.electronicExaminationState = electronicExaminationState;
    }

    public String getElectricExaminationComplete() {
        return electricExaminationComplete;
    }

    public void setElectricExaminationComplete(String electricExaminationComplete) {
        this.electricExaminationComplete = electricExaminationComplete;
    }

    public String getExternalAuditorDate() {
        return externalAuditorDate;
    }

    public void setExternalAuditorDate(String externalAuditorDate) {
        this.externalAuditorDate = externalAuditorDate;
    }

    public String getExternalAuditorPersonnel() {
        return externalAuditorPersonnel;
    }

    public void setExternalAuditorPersonnel(String externalAuditorPersonnel) {
        this.externalAuditorPersonnel = externalAuditorPersonnel;
    }

    public String getExternalAuditorState() {
        return externalAuditorState;
    }

    public void setExternalAuditorState(String externalAuditorState) {
        this.externalAuditorState = externalAuditorState;
    }

    public String getExternalAuditorComplete() {
        return externalAuditorComplete;
    }

    public void setExternalAuditorComplete(String externalAuditorComplete) {
        this.externalAuditorComplete = externalAuditorComplete;
    }

    public String getEntrustDate() {
        return entrustDate;
    }

    public void setEntrustDate(String entrustDate) {
        this.entrustDate = entrustDate;
    }

    public String getEntrustState() {
        return entrustState;
    }

    public void setEntrustState(String entrustState) {
        this.entrustState = entrustState;
    }

    public String getEntrustComplete() {
        return entrustComplete;
    }

    public void setEntrustComplete(String entrustComplete) {
        this.entrustComplete = entrustComplete;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
