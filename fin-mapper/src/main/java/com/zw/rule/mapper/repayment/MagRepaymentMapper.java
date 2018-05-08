package com.zw.rule.mapper.repayment;

import com.zw.rule.repayment.po.MagRepayment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MagRepaymentMapper {
    int deleteByPrimaryKey(String id);

    int insert(MagRepayment record);

    int insertSelective(MagRepayment record);

    MagRepayment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MagRepayment record);

    int updateByPrimaryKey(MagRepayment record);

    List<MagRepayment>  getRepayment(@Param("id") String id);

    List<MagRepayment>  getRepaymentList(Map map);
    //查询所有还款确认中的还款计划
    List<MagRepayment> getSureingRepayment(Map map);
}