package com.zw.rule.task.service;

import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.User;
import com.zw.rule.task.po.ProcessTask;

import java.util.List;
import java.util.Map;

public interface TaskMsgService {
    List<Map> getCommitTask(ParamFilter paramFilter);

    List<Map> getDetailTask(ParamFilter paramFilter);

    List<Map> getNotCommitTask(ParamFilter paramFilter);

    List<Map> getMonitor(ParamFilter paramFilter);

    int countCommitTask(Map map);

    int countNotCommitTask(Map map);

    int countMonitor(Map map);

    Boolean updateState(Map map,Long currentUserId);

    List<Map> forwardTask(ParamFilter paramFilter, Long currentUserId);

    Boolean updateTaskNodeByUser(Map map);
}
