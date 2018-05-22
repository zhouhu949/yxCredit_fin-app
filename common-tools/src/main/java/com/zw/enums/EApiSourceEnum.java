package com.zw.enums;

/**
 * api来源枚举
 * @author 陈清玉
 */
public enum EApiSourceEnum {
    MOHE("1","魔盒"),
    TODONG("2","同盾"),
    CREDIT("3","个人征信");
    private String code;
    private String name;
    EApiSourceEnum(String code, String name){
        this.code = code;
        this.name = name;
    }
    public EApiSourceEnum getByCode(String code){
        for (EApiSourceEnum sourceEnum : EApiSourceEnum.values()) {
            if(sourceEnum.code.equals(code)){
                return sourceEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
