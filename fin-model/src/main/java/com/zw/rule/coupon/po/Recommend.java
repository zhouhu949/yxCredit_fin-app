package com.zw.rule.coupon.po;

/**
 * Created by Administrator on 2017/12/11.
 */
public class Recommend {
    private String id;
    private String name;
    private String code;
    private String remarks;
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Recommend() {
    }

    public Recommend(String id, String name, String code, String remarks, String state) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.remarks = remarks;
        this.state = state;
    }
    @Override
    public String toString() {
        return "Recommend{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", remarks='" + remarks + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
