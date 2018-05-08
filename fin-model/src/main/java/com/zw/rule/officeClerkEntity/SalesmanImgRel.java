package com.zw.rule.officeClerkEntity;

/**
 * Created by zoukaixuan on 2017/12/20.
 * 办单员跟照片关联的关联表实体类
 */
public class SalesmanImgRel {
/**
 *  id varchar(200) NOT NULL,
 *  salesman_id varchar(200) DEFAULT NULL COMMENT '办单员id',
 *  img_url` varchar(300) DEFAULT NULL COMMENT '图片阿里云地址',
 *  img_type` varchar(30) DEFAULT NULL COMMENT '图片类型(0-身份证正面，1-身份证反面，2-手持身份证，3-其他图片)',
 *  state`
 */
    private String id;
    private String salesmanId;
    private String imgUrl;
    private String imgType;
    private String state;

    @Override
    public String toString() {
        return "SalesmanImgRel{" +
                "id='" + id + '\'' +
                ", salesmanId='" + salesmanId + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", imgType='" + imgType + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public SalesmanImgRel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
