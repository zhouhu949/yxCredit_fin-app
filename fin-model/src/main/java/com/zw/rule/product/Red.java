package com.zw.rule.product;

/**
 * Created by Administrator on 2017/11/17 0017.
 */
public class Red {
    private String id;//id
    private String create_time;//产品id
    private String status;//产品名称
    private String red_money;//期数
    private String redType;//0现金贷 1商品贷

    public String getRedType() {
        return redType;
    }

    public void setRedType(String redType) {
        this.redType = redType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRed_money() {
        return red_money;
    }

    public void setRed_money(String red_money) {
        this.red_money = red_money;
    }

    public String getRed_name() {
        return red_name;
    }

    public void setRed_name(String red_name) {
        this.red_name = red_name;
    }

    private String red_name;//综合费率
}
