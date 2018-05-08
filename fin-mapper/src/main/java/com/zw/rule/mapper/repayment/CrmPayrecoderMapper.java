package com.zw.rule.mapper.repayment;

//信贷订单还款记录
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.repayment.po.CrmPayrecoder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CrmPayrecoderMapper {
    int deleteByPrimaryKey(String id);

    int insert(CrmPayrecoder record);

    int insertSelective(CrmPayrecoder record);

    CrmPayrecoder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CrmPayrecoder record);

    int updateByPrimaryKey(CrmPayrecoder record);

    //查询还款记录
    List<Map> getCrmRepayRecords(ParamFilter paramFilter);

    //划扣记录
    List<Map> getTransferRecord(ParamFilter paramFilter);

    //计算已还本金
    List<Map> getAllCapitalList(String orderId);

    //开始判断今天是否已经有还款过了,防止重复
    List<Map> checkUniqueList(@Param("orderId") String orderId, @Param("currentTime") String currentTime);

    //查找已经还款的金额
    List<Map> getPaidList(String payControlId);

    //根据订单ID和paycontrol_id查询数量
    List<Map> getPayList(@Param("orderId")String orderId,@Param("payControlId")String payControlId);

    List<Map> getShouldMoney(String orderId);
}