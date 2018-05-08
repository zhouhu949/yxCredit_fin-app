package com.zw.rule.mapper.merchant;

import com.zw.rule.mapper.common.BaseMapper;
import com.zw.rule.merchantManagement.MerchantGrade;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/28.
 * 商户分级设置的持久层方法
 */
public  interface MerchantGradeMapper extends BaseMapper {
    public List<MerchantGrade> selectAllGrades(Map map);
    public int insertMerchantGrade(MerchantGrade grade);
    public int changeGradeState(Map map);
    public MerchantGrade selectOneGradeById(Map map);
    public int updateMerchantGradeById(MerchantGrade grade);

}
