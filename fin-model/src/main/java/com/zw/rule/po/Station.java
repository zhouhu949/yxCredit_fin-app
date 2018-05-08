package com.zw.rule.po;

import java.util.Date;
/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *
 *Filename：
 *
 *Description：
 *		  未知
 *Author:
 *		 李开艳
 *Finished：
 *		2017/6/12
 ********************************************************/
public class Station {
    private String id;

    private String stationName;

    private String stationDesc;

    private Integer status;

    private Date createTime;

    private long createor;

    private Date updateTime;

    private long updateor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName == null ? null : stationName.trim();
    }

    public String getStationDesc() {
        return stationDesc;
    }

    public void setStationDesc(String stationDesc) {
        this.stationDesc = stationDesc == null ? null : stationDesc.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getCreateor() {
        return createor;
    }

    public void setCreateor(long createor) {
        this.createor = createor;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public long getUpdateor() {
        return updateor;
    }

    public void setUpdateor(long updateor) {
        this.updateor = updateor;
    }

    @Override
    public String toString() {
        return "Station{" +
                "stationName='" + stationName + '\'' +
                ", stationDesc='" + stationDesc + '\'' +
                ", status=" + status +
                ", updateTime=" + updateTime +
                '}';
    }
}
