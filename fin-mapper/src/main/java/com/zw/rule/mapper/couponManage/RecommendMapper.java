package com.zw.rule.mapper.couponManage;

import com.zw.rule.coupon.po.Recommend;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/11.
 */
public interface RecommendMapper {

    public List<Recommend> getAllRecommend(Map map);
    public int insertRecommend(Recommend recommend);
    //查询所有的代码做唯一验证
    public List<String> getAllCode();
    //根据id查询单个推广渠道信息
    public Recommend selectOneById(Map map);
    //根据id来修改单个推广渠道信息
    public int updateOneById(Map map);
    //根据id来更改状态
    public int changeStateById(Map map);
    //根据id来删除一条数据
    public int deleteOneById(Map map);

    List getRecommendListList(Map map);

}
