package com.zw.rule.mapper.couponManage;

import com.zw.rule.coupon.po.CouponManage;
import com.zw.rule.coupon.po.Partners;

import java.util.List;
import java.util.Map;

public interface CouponMapper {

    List getCouponList(Map param);
    int addCoupon(CouponManage CouponManage);
    int updateCoupon(CouponManage CouponManage);
    int deleteCoupon(String id);
    int updateState(Map map);
    CouponManage getById(String id);
    List<Map> productConfig();
    List<Map> getCustomerInfo(Map map);
    List<Map> getCustomerChecked(String coupon_id);
    int deleteMagCouponInfo(String coupon_id);
    int insertMagCouponInfo(Map map);

    int addPartners(Partners partners);
    List getPartnersList(Map param);
    int updatePartners(Partners partners);
    Partners getPartnersId(String id);
}