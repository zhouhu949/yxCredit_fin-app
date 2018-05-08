package com.zw.rule.activity.service;

import com.zw.rule.activity.Activity;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/16.
 */
public interface ActivityService {

    List<Activity> getList(Map map);

    int updateActivity(Activity activity);

    int insertActivity(Activity activity);

    int delActivity(String id);

    Activity lookActivity(String id);

    int updateState(Map param);

}
