package com.zw.rule.task.service.impl;

import com.zw.rule.mapper.task.TaskOperationRecordMapper;
import com.zw.rule.task.po.TaskOperationRecord;
import com.zw.rule.task.service.TaskOperationRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月26日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) zw.<br>
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
public class TaskOperationRecordServiceImpl implements TaskOperationRecordService {

    @Resource
    private TaskOperationRecordMapper taskOperationRecordMapper;

    @Override
    public int insertTaskOperationRecord(TaskOperationRecord taskOperationRecord) {
        return this.taskOperationRecordMapper.insertTaskOperationRecord(taskOperationRecord);
    }
}

