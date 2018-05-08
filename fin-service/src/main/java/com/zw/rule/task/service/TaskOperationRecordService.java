package com.zw.rule.task.service;

import com.zw.rule.task.po.TaskOperationRecord;

/**
 * Created by Administrator on 2017/6/26.
 */
public interface TaskOperationRecordService {
    /**
     * 记录当前操作
     * @param taskOperationRecord
     * @return
     */
    int insertTaskOperationRecord(TaskOperationRecord taskOperationRecord);
}
