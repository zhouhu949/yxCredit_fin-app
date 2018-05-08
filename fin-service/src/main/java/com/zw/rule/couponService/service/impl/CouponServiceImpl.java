package com.zw.rule.couponService.service.impl;

import com.zw.rule.coupon.po.CouponManage;
import com.zw.rule.coupon.po.Partners;
import com.zw.rule.couponService.service.CouponService;
import com.zw.rule.mapper.couponManage.CouponMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/19.
 */
@Service
public class CouponServiceImpl implements CouponService {
    @Resource
    private CouponMapper couponMapper;
    public List getCouponList(Map map){
        return couponMapper.getCouponList(map);
    }
    public int addCoupon(CouponManage couponManage){
        return couponMapper.addCoupon(couponManage);
    }

    public int updateCoupon(CouponManage couponManage){
        return couponMapper.updateCoupon(couponManage);
    }
    public int deleteCoupon(String id){
        return couponMapper.deleteCoupon(id);
    }
    public int updateState(Map map){
        return couponMapper.updateState(map);
    }
    public CouponManage getById(String id){
        return couponMapper.getById(id);
    }
    public List productConfig(){
        return couponMapper.productConfig();
    }
    public List<Map> getCustomerInfo(Map map){
        return couponMapper.getCustomerInfo(map);
    }
    public List<Map> getCustomerChecked(String coupon_id){
        return couponMapper.getCustomerChecked(coupon_id);
    }
    public int deleteMagCouponInfo(String coupon_id){
        return couponMapper.deleteMagCouponInfo(coupon_id);
    }
    public int insertMagCouponInfo(Map map){
        return couponMapper.insertMagCouponInfo(map);
    }

    public  int addPartners(Partners partners){
        return couponMapper.addPartners(partners);
    }
    public List getPartnersList(Map map){
        return couponMapper.getPartnersList(map);
    }
    public int updatePartners(Partners partners){
        return couponMapper.updatePartners(partners);
    }
    public Partners getPartnersId(String id){
        return couponMapper.getPartnersId(id);
    }
}
