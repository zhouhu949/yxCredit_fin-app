package com.zw.rule.merchant;

/**
 * Created by Administrator on 2017/12/29.
 */
public class MerchantImgRel {
/**
 * `id` varchar(255) NOT NULL COMMENT 'id',
 `merchant_id` varchar(255) DEFAULT NULL COMMENT '商户(门店)id',
 `img_url` varchar(255) DEFAULT '' COMMENT '图片地址',
 `img_type` varchar(50) DEFAULT NULL COMMENT '图片类型：0-身份证账面 1-身份证反面 2-营业执照 3-门头logo照 4-室内照 5-收银台照 6-街景照 7-法人银行卡照 8-收款委托书 9-反欺诈影像',
 `state` varchar(50) DEFAULT NULL COMMENT '状态(暂时无用状态)',
 PRIMARY KEY (`id`)
 */
    private String id;
    private String merchantId;
    private String imgUrl;
    private String imgType;
    private String state;

    public MerchantImgRel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
