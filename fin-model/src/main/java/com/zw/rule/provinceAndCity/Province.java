package com.zw.rule.provinceAndCity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/6.
 */
public class Province implements Serializable{
    private int id;//id
    private String provCd;//省份编号
    private String provNm;//省份名
    private String status;//状态

    public Province() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvCd() {
        return provCd;
    }

    public void setProvCd(String provCd) {
        this.provCd = provCd;
    }

    public String getProvNm() {
        return provNm;
    }

    public void setProvNm(String provNm) {
        this.provNm = provNm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Province{" +
                "id=" + id +
                ", provCd='" + provCd + '\'' +
                ", provNm='" + provNm + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
