package com.zw.rule.commodity.po;

/**
 * Created by Administrator on 2017/12/15.
 */
public class Commodity {
    private  String id;
    private  String parentId;
    private  String merchandiseCode;
    private  String merchandiseName;
    private  String color;
    private  String descri;
    private  String creatTime;
    private  String alterTime;
    private  String state;
    private  String apex1;
    private  String apex2;
    private  String apex3;
    private  String merchandiseType;
    private  String type;
    private  String imgUrl;

    public void setId(String id) {
        this.id = id;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setMerchandiseCode(String merchandiseCode) {
        this.merchandiseCode = merchandiseCode;
    }

    public void setMerchandiseName(String merchandiseName) {
        this.merchandiseName = merchandiseName;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public void setAlterTime(String alterTime) {
        this.alterTime = alterTime;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setApex1(String apex1) {
        this.apex1 = apex1;
    }

    public void setApex2(String apex2) {
        this.apex2 = apex2;
    }

    public void setApex3(String apex3) {
        this.apex3 = apex3;
    }

    public void setMerchandiseType(String merchandiseType) {
        this.merchandiseType = merchandiseType;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public String getMerchandiseCode() {
        return merchandiseCode;
    }

    public String getMerchandiseName() {
        return merchandiseName;
    }

    public String getColor() {
        return color;
    }

    public String getDescri() {
        return descri;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public String getAlterTime() {
        return alterTime;
    }

    public String getState() {
        return state;
    }

    public String getApex1() {
        return apex1;
    }

    public String getApex2() {
        return apex2;
    }

    public String getApex3() {
        return apex3;
    }

    public String getMerchandiseType() {
        return merchandiseType;
    }

    public String getType() {
        return type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Commodity(String id, String parentId, String merchandiseCode, String merchandiseName, String color, String descri, String creatTime, String alterTime, String state, String apex1, String apex2, String apex3, String merchandiseType, String type, String imgUrl) {
        this.id = id;
        this.parentId = parentId;
        this.merchandiseCode = merchandiseCode;
        this.merchandiseName = merchandiseName;
        this.color = color;
        this.descri = descri;
        this.creatTime = creatTime;
        this.alterTime = alterTime;
        this.state = state;
        this.apex1 = apex1;
        this.apex2 = apex2;
        this.apex3 = apex3;
        this.merchandiseType = merchandiseType;
        this.type = type;
        this.imgUrl = imgUrl;
    }

    public Commodity() {}
}
