package com.zw.rule.mapper.activity;

import com.zw.rule.activity.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {

    List<Activity> getList(Map param);

    int updateActivity(Activity activity);

    int insertActivity(Activity activity);

    int delActivity(String id);

    Activity lookActivity(String id);

    int updateState(Map param);
}