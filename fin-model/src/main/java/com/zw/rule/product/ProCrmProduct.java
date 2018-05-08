package com.zw.rule.product;

public class ProCrmProduct {
    private String id;

    private String create_time;

    private String emp_id;

    private String type;

    private String pro_name;

    private String pro_quota;

    private String pro_describe;

    private String pro_number;

    private String parent_id;

    private String status;

    private String provinces;

    private String city;

    private String distric;

    private String provinces_id;

    private String city_id;

    private String distric_id;
    private String img_url; // 图片保存名称
    private String pro_quota_limit; // 产品额度上限
    private String pro_quota_proportion;//产品提额比例
    private String pro_series_type;//平台类型

    public String getPro_series_type() {
        return pro_series_type;
    }

    public void setPro_series_type(String pro_series_type) {
        this.pro_series_type = pro_series_type;
    }

    public String getPro_quota_proportion() {
        return pro_quota_proportion;
    }

    public void setPro_quota_proportion(String pro_quota_proportion) {
        this.pro_quota_proportion = pro_quota_proportion;
    }

    public String getPro_quota_limit() {
        return pro_quota_limit;
    }

    public void setPro_quota_limit(String pro_quota_limit) {
        this.pro_quota_limit = pro_quota_limit;
    }
    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time == null ? null : create_time.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id == null ? null : emp_id.trim();
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name == null ? null : pro_name.trim();
    }

    public String getPro_quota() {
        return pro_quota;
    }

    public void setPro_quota(String pro_quota) {
        this.pro_quota = pro_quota;
    }

    public String getPro_number() {
        return pro_number;
    }

    public void setPro_number(String pro_number) {
        this.pro_number = pro_number == null ? null : pro_number.trim();
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id == null ? null : parent_id.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistric() {
        return distric;
    }

    public void setDistric(String distric) {
        this.distric = distric;
    }

    public String getProvinces_id() {
        return provinces_id;
    }

    public void setProvinces_id(String provinces_id) {
        this.provinces_id = provinces_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getDistric_id() {
        return distric_id;
    }

    public void setDistric_id(String distric_id) {
        this.distric_id = distric_id;
    }
    public String getPro_describe() {
        return pro_describe;
    }

    public void setPro_describe(String pro_describe) {
        this.pro_describe = pro_describe;
    }
}