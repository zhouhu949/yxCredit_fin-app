package com.zw.rule.reclaimMerchant;

import java.util.Date;

/**
 * Created by liuxiaolong on 2017/12/20.
 * 黄金商城实体类
 */
public class ReclaimMerchant {

    private String id;//id
    private String depId;
    private String merName;
    private String merLogo;
    private String merInfo;
    private String status;
    private String updateUser;
    private String updateTime;
   // private Date updateTime;

    public ReclaimMerchant() {

    }

    public ReclaimMerchant(String id, String depId, String merName, String merLogo, String merInfo, String status, String updateUser, String updateTime) {
        this.id = id;
        this.depId = depId;
        this.merName = merName;
        this.merLogo = merLogo;
        this.merInfo = merInfo;
        this.status = status;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getMerLogo() {
        return merLogo;
    }

    public void setMerLogo(String merLogo) {
        this.merLogo = merLogo;
    }

    public String getMerInfo() {
        return merInfo;
    }

    public void setMerInfo(String merInfo) {
        this.merInfo = merInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }




    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}

