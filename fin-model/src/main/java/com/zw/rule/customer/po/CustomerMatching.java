package com.zw.rule.customer.po;

/**
 * 小区信息
 */
public class CustomerMatching {
    private String id;//主键

    private String cellName;//小区名称

    private String averageHousePrice;//小区平均房价 (元/㎡)

    private String averageRenovationPrice;//小区平均装修价格 (元/㎡)

    private String provincesId;//省id

    private String cityId;//市id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName == null ? null : cellName.trim();
    }

    public String getAverageHousePrice() {
        return averageHousePrice;
    }

    public void setAverageHousePrice(String averageHousePrice) {
        this.averageHousePrice = averageHousePrice == null ? null : averageHousePrice.trim();
    }

    public String getAverageRenovationPrice() {
        return averageRenovationPrice;
    }

    public void setAverageRenovationPrice(String averageRenovationPrice) {
        this.averageRenovationPrice = averageRenovationPrice == null ? null : averageRenovationPrice.trim();
    }

    public String getProvincesId() {
        return provincesId;
    }

    public void setProvincesId(String provincesId) {
        this.provincesId = provincesId == null ? null : provincesId.trim();
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId == null ? null : cityId.trim();
    }
}