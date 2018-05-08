package com.zw.rule.servicePackage.po;

/**
 * Created by Administrator on 2017/11/28.
 */
public class ServicePackage {
    private String id;
    private String package_type_name;
    private String repayment_type;
    private String info;
    private String creat_time;
    private String alter_time;

    public String getId() {
        return id;
    }

    public String getPackage_type_name() {
        return package_type_name;
    }

    public String getRepayment_type() {
        return repayment_type;
    }

    public String getInfo() {
        return info;
    }

    public String getCreat_time() {
        return creat_time;
    }

    public String getAlter_time() {
        return alter_time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPackage_type_name(String package_type_name) {
        this.package_type_name = package_type_name;
    }

    public void setRepayment_type(String repayment_type) {
        this.repayment_type = repayment_type;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setCreat_time(String creat_time) {
        this.creat_time = creat_time;
    }

    public void setAlter_time(String alter_time) {
        this.alter_time = alter_time;
    }

}
