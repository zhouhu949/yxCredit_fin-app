package com.zw.rule.areaQuota;

/**
 * 区域限额设置实体类
 * Created by Administrator on 2017/12/6.
 */
public class AreaQuota {
    private String id;
    private String provinceId;    //省份id
    private String provinceName;//省份名
    private String cityId;        //城市id
    private String cityName;    //城市名
    private String state;       //状态
    private String numberDay;   //日进件量限额
    private String numberWeek;  //周进件量限额
    private String numberMonth; //月进件量限额
    private String quotaDay;    //日申请额度限额
    private String quotaWeek;   //周申请额度限额
    private String quotaMonth;  //月申请额度限额
    private String singleQuota; //单笔申请额度限额

    public AreaQuota() {
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getNumberDay() {
        return numberDay;
    }

    public String getNumberWeek() {
        return numberWeek;
    }

    public String getNumberMonth() {
        return numberMonth;
    }

    public String getQuotaDay() {
        return quotaDay;
    }

    public String getQuotaWeek() {
        return quotaWeek;
    }

    public String getQuotaMonth() {
        return quotaMonth;
    }

    public String getSingleQuota() {
        return singleQuota;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setNumberDay(String numberDay) {
        this.numberDay = numberDay;
    }

    public void setNumberWeek(String numberWeek) {
        this.numberWeek = numberWeek;
    }

    public void setNumberMonth(String numberMonth) {
        this.numberMonth = numberMonth;
    }

    public void setQuotaDay(String quotaDay) {
        this.quotaDay = quotaDay;
    }

    public void setQuotaWeek(String quotaWeek) {
        this.quotaWeek = quotaWeek;
    }

    public void setQuotaMonth(String quotaMonth) {
        this.quotaMonth = quotaMonth;
    }

    public void setSingleQuota(String singleQuota) {
        this.singleQuota = singleQuota;
    }

    @Override
    public String toString() {
        return "AreaQuota{" +
                "id='" + id + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", state='" + state + '\'' +
                ", numberDay='" + numberDay + '\'' +
                ", numberWeek='" + numberWeek + '\'' +
                ", numberMonth='" + numberMonth + '\'' +
                ", quotaDay='" + quotaDay + '\'' +
                ", quotaWeek='" + quotaWeek + '\'' +
                ", quotaMonth='" + quotaMonth + '\'' +
                ", singleQuota='" + singleQuota + '\'' +
                '}';
    }
}
