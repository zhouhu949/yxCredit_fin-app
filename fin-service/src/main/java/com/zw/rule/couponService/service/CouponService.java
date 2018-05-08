package com.zw.rule.couponService.service;

import com.zw.rule.coupon.po.CouponManage;
import com.zw.rule.coupon.po.Partners;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/19.
 */
public interface CouponService {
    List getCouponList(Map map);
    int addCoupon(CouponManage CouponManage);
    int updateCoupon(CouponManage CouponManage);
    int deleteCoupon(String id);
    int updateState(Map map);
    CouponManage getById(String id);
    List productConfig();
    List<Map> getCustomerInfo(Map map);
    List<Map> getCustomerChecked(String coupon_id);
    int deleteMagCouponInfo(String coupon_id);
    int insertMagCouponInfo(Map map);

    int addPartners(Partners partners);
    List getPartnersList(Map map);
    int updatePartners(Partners partners);
    Partners getPartnersId(String id);
}
