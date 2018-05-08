package com.zw.rule.magServicePackage.po;

/**
 * Created by Administrator on 2017/12/6.
 */
public class MagServicePackage {
    private String id;
    private String packageId;
    private String packageName;
    private String periodCollection;
    private String month;
    private String amountCollection;
    private String state;
    private String apex1;
    private String periodCollectionType;
    private String forceCollection;

    public String getPeriodCollectionType() {
        return periodCollectionType;
    }

    public void setPeriodCollectionType(String periodCollectionType) {
        this.periodCollectionType = periodCollectionType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMonth(String month) {
        this.month = month;
    }


    public void setState(String state) {
        this.state = state;
    }

    public void setApex1(String apex1) {
        this.apex1 = apex1;
    }

    public String getId() {
        return id;
    }


    public String getMonth() {
        return month;
    }


    public String getState() {
        return state;
    }

    public String getApex1() {
        return apex1;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPeriodCollection() {
        return periodCollection;
    }

    public void setPeriodCollection(String periodCollection) {
        this.periodCollection = periodCollection;
    }

    public String getAmountCollection() {
        return amountCollection;
    }

    public void setAmountCollection(String amountCollection) {
        this.amountCollection = amountCollection;
    }


    public String getForceCollection() {
        return forceCollection;
    }

    public void setForceCollection(String forceCollection) {
        this.forceCollection = forceCollection;
    }
}
