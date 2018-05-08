package com.zw.rule.couponService.service;

import com.zw.rule.coupon.po.Recommend;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/11.
 */
public interface CouponRecommendService {
    //查询全部
    public List<Recommend> getAllRecommend(Map map);
    //查询所有的code
    public List<String> getAllCodes();
    //添加
    public int addRecommend(Recommend recommend);
    //根据id查询单个信息
    public Recommend getOneRecommendById(Map map);
    //根据id来更改单个推广渠道信息
    public int changeRecommendById(Map map);
    //根据id来更改状态
    public int changeRecommendState(Map map);
    //根据id来删除一条推广管理信息
    public int deleteOneRecommendById(Map map);

    List getRecommendListList(Map map);

    /**
     * 生成二维码
     * @param callback 二维码回调路径
     * @return 二维码
     */
    String createQRCode(String callback);
}
