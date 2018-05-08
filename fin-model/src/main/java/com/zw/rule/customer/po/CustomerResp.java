package com.zw.rule.customer.po;

/**
 * 装修合理性
 */
public class CustomerResp {
    private String id;//主键

    private String orderId;//订单id

    private String customerId;//客户id

    private String materialPriceResp;//材料价格是否合理（1-是，0-否）

    private String materialCountResp;//材料数量是否合理（1-是，0-否）

    private String decorationCountResp;//装修清单数量是否合理（1-是，0-否）

    private String materialName;//材料名称

    private String decorationImg;//材料清单比较图片

    private String remark;//备注

    private String creatTime;//创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getMaterialPriceResp() {
        return materialPriceResp;
    }

    public void setMaterialPriceResp(String materialPriceResp) {
        this.materialPriceResp = materialPriceResp == null ? null : materialPriceResp.trim();
    }

    public String getMaterialCountResp() {
        return materialCountResp;
    }

    public void setMaterialCountResp(String materialCountResp) {
        this.materialCountResp = materialCountResp == null ? null : materialCountResp.trim();
    }

    public String getDecorationCountResp() {
        return decorationCountResp;
    }

    public void setDecorationCountResp(String decorationCountResp) {
        this.decorationCountResp = decorationCountResp == null ? null : decorationCountResp.trim();
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
    }

    public String getDecorationImg() {
        return decorationImg;
    }

    public void setDecorationImg(String decorationImg) {
        this.decorationImg = decorationImg == null ? null : decorationImg.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime == null ? null : creatTime.trim();
    }
}