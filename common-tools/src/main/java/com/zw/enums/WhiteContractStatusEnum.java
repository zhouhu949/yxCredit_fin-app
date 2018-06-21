package com.zw.enums;

/**
 * 白名单职务枚举
 * @author 陈淸玉
 */
public enum WhiteContractStatusEnum {
    /**
     * 有
     */
    EXIST(1,"有"),
    /**
     * 没有
     */
    NOT_EXIST(0,"无");

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

    WhiteContractStatusEnum(Integer code, String label){
        this.label = label;
        this.code = code;
    }

    public static WhiteContractStatusEnum getByLabel(String label){
        for (WhiteContractStatusEnum statusEnum : WhiteContractStatusEnum.values()) {
            if(statusEnum.getLabel().equals(label)){
                return  statusEnum;
            }
        }
        return null;
    }
    public static WhiteContractStatusEnum getByCode(Integer code){
        for (WhiteContractStatusEnum statusEnum : WhiteContractStatusEnum.values()) {
            if(statusEnum.getCode().equals(code)){
                return  statusEnum;
            }
        }
        return null;
    }

}