package com.zw.rule.product;

/**
 * Created by Administrator on 2017/7/15.
 */
public class Fee {
    private String id;//id
    private String productId;//产品id
    private String productAmount;//产品名称
    private String productPeriods;//期数
    private String productComFee;//综合费率
    private String li_xi;//利息
    private String zhifu_fee;//支付服务费
    private String zhanghu_fee;//账户管理费
    private String fengxian_fee;//风险评估费
    private String shenhe_fee;//审核服务费
    private String zhina_fee;//滞纳金
    private String yuqi_fee;//逾期罚息
    private String zongheri_fee;//综合日费率
    private String state;//状态    删除：-1；正常：0；
    private String year_rate;//年利率
    private String month_rate;//月利率   删除：-1；正常：0；

    public String getYear_rate() {
        return year_rate;
    }

    public void setYear_rate(String year_rate) {
        this.year_rate = year_rate;
    }

    public String getMonth_rate() {
        return month_rate;
    }

    public void setMonth_rate(String month_rate) {
        this.month_rate = month_rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductPeriods() {
        return productPeriods;
    }

    public void setProductPeriods(String productPeriods) {
        this.productPeriods = productPeriods;
    }

    public String getProductComFee() {
        return productComFee;
    }

    public void setProductComFee(String productComFee) {
        this.productComFee = productComFee;
    }

    public String getLi_xi() {
        return li_xi;
    }

    public void setLi_xi(String li_xi) {
        this.li_xi = li_xi;
    }

    public String getZhifu_fee() {
        return zhifu_fee;
    }

    public void setZhifu_fee(String zhifu_fee) {
        this.zhifu_fee = zhifu_fee;
    }

    public String getZhanghu_fee() {
        return zhanghu_fee;
    }

    public void setZhanghu_fee(String zhanghu_fee) {
        this.zhanghu_fee = zhanghu_fee;
    }

    public String getFengxian_fee() {
        return fengxian_fee;
    }

    public void setFengxian_fee(String fengxian_fee) {
        this.fengxian_fee = fengxian_fee;
    }

    public String getShenhe_fee() {
        return shenhe_fee;
    }

    public void setShenhe_fee(String shenhe_fee) {
        this.shenhe_fee = shenhe_fee;
    }

    public String getZhina_fee() {
        return zhina_fee;
    }

    public void setZhina_fee(String zhina_fee) {
        this.zhina_fee = zhina_fee;
    }

    public String getYuqi_fee() {
        return yuqi_fee;
    }

    public void setYuqi_fee(String yuqi_fee) {
        this.yuqi_fee = yuqi_fee;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZongheri_fee() {
        return zongheri_fee;
    }

    public void setZongheri_fee(String zongheri_fee) {
        this.zongheri_fee = zongheri_fee;
    }
}
