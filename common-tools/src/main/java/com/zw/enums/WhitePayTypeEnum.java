package com.zw.enums;

/**
 * 白名单工资发放形式枚举
 * @author 陈淸玉
 */
public enum WhitePayTypeEnum {
    /**
     * 现金
     */
    READY(1,"现金"),
    /**
     * 打卡
     */
    BANK_CARD(2,"打卡"),
    /**
     * 其他（都有）
     */
    OTHER(3,"均有");

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

    WhitePayTypeEnum(Integer code, String label){
        this.label = label;
        this.code = code;
    }

    public static  WhitePayTypeEnum getByLabel(String label){
        for (WhitePayTypeEnum payTypeEnum : WhitePayTypeEnum.values()) {
            if(payTypeEnum.getLabel().equals(label)){
                return  payTypeEnum;
            }
        }
        return null;
    }
    public static WhitePayTypeEnum getByCode(Integer code){
        for (WhitePayTypeEnum payTypeEnum : WhitePayTypeEnum.values()) {
            if(payTypeEnum.getCode().equals(code)){
                return  payTypeEnum;
            }
        }
        return null;
    }
}
