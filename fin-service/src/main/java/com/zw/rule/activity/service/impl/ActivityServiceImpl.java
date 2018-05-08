package com.zw.rule.activity.service.impl;

import com.zw.rule.activity.Activity;
import com.zw.rule.activity.service.ActivityService;
import com.zw.rule.mapper.activity.ActivityMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/16.
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private ActivityMapper activityMapper;

    @Override
    public List<Activity> getList(Map map) {
        return activityMapper.getList(map);
    }

    public int updateActivity(Activity activity){
        return activityMapper.updateActivity(activity);
    }
    public int insertActivity(Activity activity){
        return activityMapper.insertActivity(activity);
    }
    public int delActivity(String id){
        return activityMapper.delActivity(id);
    }
    public Activity lookActivity(String id){
        return activityMapper.lookActivity(id);
    }
    public int updateState(Map param){
        return activityMapper.updateState(param);
    }

}
