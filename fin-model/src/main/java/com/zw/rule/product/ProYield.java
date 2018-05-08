package com.zw.rule.product;

/**
 * Created by Administrator on 2017/11/17 0017.
 */
public class ProYield {
    private String id;//id
    private String create_time;//产品id
    private String status;//产品名称
    private String pro_quota_limit;//期数
    private String pro_quota_proportion;//综合费率

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

    public String getPro_quota_limit() {
        return pro_quota_limit;
    }

    public void setPro_quota_limit(String pro_quota_limit) {
        this.pro_quota_limit = pro_quota_limit;
    }

    public String getPro_quota_proportion() {
        return pro_quota_proportion;
    }

    public void setPro_quota_proportion(String pro_quota_proportion) {
        this.pro_quota_proportion = pro_quota_proportion;
    }
}
