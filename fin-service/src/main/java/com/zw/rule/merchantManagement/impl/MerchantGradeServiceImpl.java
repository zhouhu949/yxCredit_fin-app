package com.zw.rule.merchantManagement.impl;

import com.zw.rule.mapper.merchant.MerchantGradeMapper;
import com.zw.rule.merchantManagement.MerchantGrade;
import com.zw.rule.merchantManagement.MerchantGradeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/4.
 */
@Service
public class MerchantGradeServiceImpl implements MerchantGradeService {
    @Resource
    public MerchantGradeMapper mapper;

    /**
     * 获取全部的商户分级列表
     * @param map
     * @return
     */
    public List<MerchantGrade> getAllMerchantGrades(Map map){
        return mapper.selectAllGrades(map);
    }

    /**
     * 添加商户分级
     * @param grade
     * @return
     */
    public int addMerchantGrade(MerchantGrade grade){
        return mapper.insertMerchantGrade(grade);
    }
    /**
     * 更改商户等级状态()
     */
    public int sartOrStopGrade(Map map){
        return mapper.changeGradeState(map);
    }
    /**
     * 获取单个商户设置信息
     */
   public MerchantGrade getOneGradeById(Map map){
        return mapper.selectOneGradeById(map);
    }
    /**
     * 修改商户等级配置信息updateMerchantGradeById
     */
    public int changeOneGradeById(MerchantGrade grade){
        return mapper.updateMerchantGradeById(grade);
    }

}
