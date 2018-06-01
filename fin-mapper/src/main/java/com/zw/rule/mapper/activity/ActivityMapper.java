package com.zw.rule.mapper.activity;

import com.zw.rule.activity.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {
    /**********************************************碧友信*******************************************************/

    /**
     * 获取Banner列表
     * @author 仙海峰
     * @param param
     * @return
     */
    List<Activity> getList(Map param);

    /**
     * 修改Banner
     * @author 仙海峰
     * @param activity
     * @return
     */
    int updateActivity(Activity activity);

    /**
     * 添加Banner
     * @author 仙海峰
     * @param activity
     * @return
     */
    int insertActivity(Activity activity);

    /**
     * 删除Banner
     * @author 仙海峰
     * @param id
     * @return
     */
    int delActivity(String id);

    /**
     * 查看Banner
     * @author 仙海峰
     * @param id
     * @return
     */
    Activity lookActivity(String id);

    /**
     * 修改Banner状态（上架/下架）
     * @author 仙海峰
     * @param param
     * @return
     */
    int updateState(Map param);
}