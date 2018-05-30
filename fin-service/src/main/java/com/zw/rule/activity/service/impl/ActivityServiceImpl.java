package com.zw.rule.activity.service.impl;

import com.zw.UploadFile;
import com.zw.base.util.GeneratePrimaryKeyUtils;
import com.zw.rule.activity.Activity;
import com.zw.rule.activity.service.ActivityService;
import com.zw.rule.mapper.activity.ActivityMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/30.
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Value("${byx.img.path}")
    private String imgPath;

    @Value("${byx.img.activity}")
    private String activityImg;
    @Resource
    private ActivityMapper activityMapper;


    /**********************************************碧友信*******************************************************/

    /**
     * 获取Banner列表
     * @author 仙海峰
     * @param map
     * @return
     */
    @Override
    public List<Activity> getList(Map map) {
        return activityMapper.getList(map);
    }

    /**
     * 修改Banner
     * @author 仙海峰
     * @param activity
     * @return
     */
    @Override
    public int updateActivity(Activity activity){
        return activityMapper.updateActivity(activity);
    }

    /**
     * 添加Banner
     * @author 仙海峰
     * @param activity
     * @return
     */
    @Override
    public int insertActivity(Activity activity){
        return activityMapper.insertActivity(activity);
    }

    /**
     * 删除Banner
     * @author 仙海峰
     * @param id
     * @return
     */
    @Override
    public int delActivity(String id){
        return activityMapper.delActivity(id);
    }

    /**
     * 查看Banner
     * @author 仙海峰
     * @param id
     * @return
     */
    @Override
    public Activity lookActivity(String id){
        return activityMapper.lookActivity(id);
    }

    /**
     * 修改Banner状态（上架/下架）
     * @author 仙海峰
     * @param param
     * @return
     */
    @Override
    public int updateState(Map param){
        return activityMapper.updateState(param);
    }

    /**
     * 上传图片
     * @author 仙海峰
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public List uploadaCtivityImage(HttpServletRequest request) throws Exception {
        String fileName="";
        String id = GeneratePrimaryKeyUtils.getUUIDKey();//新的文件名
        //获取根目录
        //String root = request.getSession().getServletContext().getRealPath("/");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String currentDateStr = format.format(new Date());
        String newFilePath = imgPath + File.separator + activityImg + currentDateStr;//文件保存路径url
        Map<String, Object> allMap = UploadFile.getFile(request, newFilePath, id);
        List<Map<String, Object>> list = (List<Map<String, Object>>) allMap.get("fileList");
        //当前台有文件时，给图片名称变量赋值
        if (!list.isEmpty()) {
            Map<String, Object> fileMap = list.get(0);
            fileName = activityImg + currentDateStr+"/"+ fileMap.get("Name").toString();
        }
        List imageList = new ArrayList();
        imageList.add(fileName);
        return imageList;
    }

}
