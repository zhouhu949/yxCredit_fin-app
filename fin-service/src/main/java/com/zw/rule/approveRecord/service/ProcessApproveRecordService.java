package com.zw.rule.approveRecord.service;

import com.zw.rule.approveRecord.po.ProcessApproveRecord;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.process.po.ProcessNode;
import com.zw.rule.product.ProCrmProduct;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/15.
 */
public interface ProcessApproveRecordService {
    int insertProcessApproveRecord(ProcessApproveRecord processApproveRecord);
    List processApproveRecodList(ParamFilter paramFilter);
}
