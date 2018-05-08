package com.zw.rule.mapper.repayment;


import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.repayment.po.CrmPaycontrol;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

//信贷还款明细表
public interface CrmPaycontrolMapper {
    int deleteByPrimaryKey(String id);

    int insert(CrmPaycontrol crmPaycontrol);

    int insertSelective(CrmPaycontrol crmPaycontrol);

    CrmPaycontrol selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CrmPaycontrol crmPaycontrol);

    int updateByPrimaryKey(CrmPaycontrol crmPaycontrol);

    //查询信贷还款明细
    List<Map> getCrmPaycontrolList(ParamFilter paramFilter);

    //根据订单ID和状态查询数量
    List<Map> getDetailByStatus(@Param("crmOrderId") String crmOrderId, @Param("status") int status);

    //查询本期数据
    List<Map> getCurrentList(String crmOrderId);

    //获得最新一期 还款明细
    List<CrmPaycontrol> getPayList(String crmOrderId);

    //获得待还明细总金额
    List<Map> getPayMoney(String crmOrderId);

    //获得部分逾期还款明细
    List<Map> getOverMoney(String crmOrderId);

    //逾期还款有效明细
    List<Map> getOverRepayMoney(String crmOrderId);

    List<Map> getShouldMoney(String crmOrderId);

    List<Map> countShouldDuetime(String crmOrderId);

    List<Map> shouldCapitalManageMoney(String crmOrderId);

    //待还期数
    List<Map> getPerPayPeriods(String crmOrderId);

    //所有期数
    List<Map> getAllPeriods(String crmOrderId);

    List<Map> getOneShouldMoney(String crmOrderId);

    List<CrmPaycontrol> selectByOrderId(String crmOrderId);

    //开始检测当天的状态
    List<Map> getCurrentStatus(@Param("orderId") String orderId, @Param("currentTime") String currentTime);

    //开始检测当天的状态
    List<Map> getCurrentDueTime(@Param("orderId") String orderId, @Param("currentTime") String currentTime);
}