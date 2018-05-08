package com.zw.rule.mapper.approveRecord;

import com.zw.rule.approveRecord.po.ProcessApproveRecord;
import com.zw.rule.mybatis.ParamFilter;

import java.util.List;

public interface ProcessApproveRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProcessApproveRecord record);

    int insertSelective(ProcessApproveRecord record);

    ProcessApproveRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProcessApproveRecord record);

    int updateByPrimaryKeyWithBLOBs(ProcessApproveRecord record);

    int updateByPrimaryKey(ProcessApproveRecord record);

    List findByOrderId(String orderId);

    List findByOrderIdPage(ParamFilter paramFilter);
}