package com.zw.enums;

/**
 * 白名单职务枚举
 * @author 陈淸玉
 */
public enum  WhiteJobEnum {
    /**
     * 包工头
     */
    FOREMAN(1,"包工头"),
    /**
     * 农民工
     */
    FARMER(2,"农民工"),
    /**
     * 临时工
     */
    TEMPORARY(3,"临时工");

    private String label;

    private Integer code;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    WhiteJobEnum(Integer code, String label){
        this.label = label;
        this.code = code;
    }

    public static WhiteJobEnum getByLabel(String label){
        for (WhiteJobEnum jobEnum : WhiteJobEnum.values()) {
            if(jobEnum.getLabel().equals(label)){
                return  jobEnum;
            }
        }
        return null;
    }
    public static WhiteJobEnum getByCode(Integer code){
        for (WhiteJobEnum jobEnum : WhiteJobEnum.values()) {
            if(jobEnum.getCode().equals(code)){
                return  jobEnum;
            }
        }
        return null;
    }
}
