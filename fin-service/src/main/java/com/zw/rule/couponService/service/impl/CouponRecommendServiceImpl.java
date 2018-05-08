package com.zw.rule.couponService.service.impl;

import com.zw.rule.coupon.po.Recommend;
import com.zw.rule.couponService.service.CouponRecommendService;
import com.zw.rule.mapper.couponManage.RecommendMapper;
import com.zw.rule.util.QRCodeTool;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/11.
 */
@Service
public class CouponRecommendServiceImpl implements CouponRecommendService {
    private static final Logger logger = Logger.getLogger(CouponRecommendServiceImpl.class);

    @Resource
    private RecommendMapper mapper;

    /**
     * 查询全部
     * @param map
     * @return
     */
    public List<Recommend> getAllRecommend(Map map) {
        return mapper.getAllRecommend(map);
    }

    /**
     * 添加方法
     * @param recommend
     * @return
     */
    public int addRecommend(Recommend recommend){
        return mapper.insertRecommend(recommend);
    }

    /**
     * 根据id查询单个推广渠道信息
     * @param map
     * @return
     */
    public Recommend getOneRecommendById(Map map){
        return mapper.selectOneById(map);
    }

    /**
     * 根据id来更改单条信息
     * @param map
     * @return
     */
    public int changeRecommendById(Map map) {
        return mapper.updateOneById(map);
    }

    /**
     * 查询所有的code
     * @return
     */
    public List<String> getAllCodes(){
        return mapper.getAllCode();
    }
    /**
     * 更改推广状态 根据id
     * @param map
     * @return
     */
    public int changeRecommendState(Map map){
        return mapper.changeStateById(map);
    }

    /**
     * 根据id来删除一条推广管理信息
     * @param map
     * @return
     */
    public int deleteOneRecommendById(Map map){
        return mapper.deleteOneById(map);
    }

    @Override
    public List getRecommendListList(Map map){
        return  mapper.getRecommendListList(map);
    }

    /**
     * 生成二维码
     * @param callback 二维码回调路径
     * @return 二维码
     */
    public String createQRCode(String callback)
    {
        String img = null;
        try
        {
            img = QRCodeTool.encodeBase64(callback, null);
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        return img;
    }
}
