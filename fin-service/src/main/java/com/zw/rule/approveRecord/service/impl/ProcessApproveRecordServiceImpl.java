package com.zw.rule.approveRecord.service.impl;


import com.zw.rule.approveRecord.po.ProcessApproveRecord;
import com.zw.rule.approveRecord.service.ProcessApproveRecordService;
import com.zw.rule.mapper.approveRecord.ProcessApproveRecordMapper;
import com.zw.rule.mybatis.ParamFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <strong>Title :<br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月12日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Administrator <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
@Service
public class ProcessApproveRecordServiceImpl implements ProcessApproveRecordService {
    private static final Logger logger = Logger.getLogger(ProcessApproveRecordServiceImpl.class);

    @Autowired
    private ProcessApproveRecordMapper processApproveRecordMapper;

    @Override
    public int insertProcessApproveRecord(ProcessApproveRecord processApproveRecord) {
        return processApproveRecordMapper.insertSelective(processApproveRecord);
    }

    @Override
    public List processApproveRecodList(ParamFilter paramFilter) {
        return processApproveRecordMapper.findByOrderIdPage(paramFilter);
    }
}

