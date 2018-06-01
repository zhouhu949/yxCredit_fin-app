package com.zw.rule.activity.service;

import com.zw.rule.activity.Activity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/16.
 */
public interface ActivityService {
    /**********************************************碧友信*******************************************************/

    /**
     * 获取Banner列表
     * @author 仙海峰
     * @param map
     * @return
     */
    List<Activity> getList(Map map);

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

    /**
     * 上传图片
     * @author 仙海峰
     * @param request
     * @return
     * @throws Exception
     */
    List uploadaBannerImage(HttpServletRequest request) throws Exception ;

}
