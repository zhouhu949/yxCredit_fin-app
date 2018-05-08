package com.zw.rule.provinceAndCity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/6.
 */
public class City implements Serializable {
    private int id;//id
    private String cityCd;//城市编号
    private String cityNm;//城市名称
    private int pid;//关联的省份id
    private String status;//状态

    public City() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityCd() {
        return cityCd;
    }

    public void setCityCd(String cityCd) {
        this.cityCd = cityCd;
    }

    public String getCityNm() {
        return cityNm;
    }

    public void setCityNm(String cityNm) {
        this.cityNm = cityNm;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", cityCd='" + cityCd + '\'' +
                ", cityNm='" + cityNm + '\'' +
                ", pid=" + pid +
                ", status='" + status + '\'' +
                '}';
    }
}
