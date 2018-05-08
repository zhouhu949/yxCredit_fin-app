package com.zw.rule.merchantBlack.service.impl;
import com.zw.rule.mapper.merchant.MerchantGradeMapper;
import com.zw.rule.mapper.merchantBlackMapper.MerchantBlackMapper;
import com.zw.rule.merchantBlack.service.MerchantBlackService;
import com.zw.rule.merchantBlackEntity.MerchantBlack;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/9.
 */
@Service
public class MerchantBlackServiceImpl implements MerchantBlackService {
    @Resource
    private MerchantBlackMapper mapper;

    /**
     * 查询全部的商户黑名单配置信息列表
     * @return
     */
    public List<MerchantBlack> getAllMerchantBlack(Map map){
        return mapper.selectAllMerchantBlack(map);
    };

    /**
     * 添加一条商户黑名单配置数据
     * @param merchantBlack
     * @return
     */
    public int addoneMerchantBlack(MerchantBlack merchantBlack){
        return mapper.insertOneMerchantBlack(merchantBlack);
    }

    /**
     * 停用或者启用
     * @param map
     * @return
     */
    //更改状态 停用或者启用
    public int startOrStopState(Map map){
        return mapper.changeState(map);
    }
    //根据id单个查询商户黑名单信息
    public MerchantBlack getOneBlackById(String id){
        return mapper.selectOneMerchantBlackById(id);
    }
    //根据id更改
    public int changeBlackById(MerchantBlack merchantBlack){
        return mapper.changeOneMerchantBlackById(merchantBlack);
    }
    //根据id删除
    public int deleteBlackById(Map map){
        return mapper.deleteOneMerchantBlackById(map);
    }
}
