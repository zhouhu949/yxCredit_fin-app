package com.zw.rule.mapper.repayment;


import com.zw.rule.repayment.po.DerateManage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AccDerateManageMapper {
    int deleteByPrimaryKey(String ID);

    int insert(DerateManage record);

    int insertSelective(DerateManage record);

    DerateManage selectByPrimaryKey(String ID);

    int updateByPrimaryKeySelective(DerateManage record);

    int updateByPrimaryKey(DerateManage record);

    List<Map> getPayApplyInfoFromType(@Param("crmOrderId") String crmOrderId, @Param("type")String type);

    //判断是否含有其他的还款申请
    List<Map> getOtherPay(String crmOrderId);
}