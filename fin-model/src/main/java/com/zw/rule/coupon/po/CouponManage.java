package com.zw.rule.coupon.po;

/**
 * Created by Administrator on 2017/9/21.
 */
public class CouponManage {
    private String id;
    private String coupon_name;
    private String coupon_amount;
    private String coupon_type;
    private String coupon_desc;
    private String coupon_state;
    private String coupon_effective_time;
    private String is_del;
    private String crm_product_id;
    private String create_time;
    private String alter_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public String getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(String coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public String getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(String coupon_type) {
        this.coupon_type = coupon_type;
    }

    public String getCoupon_desc() {
        return coupon_desc;
    }

    public void setCoupon_desc(String coupon_desc) {
        this.coupon_desc = coupon_desc;
    }

    public String getCoupon_state() {
        return coupon_state;
    }

    public void setCoupon_state(String coupon_state) {
        this.coupon_state = coupon_state;
    }

    public String getCoupon_effective_time() {
        return coupon_effective_time;
    }

    public void setCoupon_effective_time(String coupon_effective_time) {
        this.coupon_effective_time = coupon_effective_time;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAlter_time() {
        return alter_time;
    }

    public void setAlter_time(String alter_time) {
        this.alter_time = alter_time;
    }

    public String getCrm_product_id() {
        return crm_product_id;
    }

    public void setCrm_product_id(String crm_product_id) {
        this.crm_product_id = crm_product_id;
    }
}
