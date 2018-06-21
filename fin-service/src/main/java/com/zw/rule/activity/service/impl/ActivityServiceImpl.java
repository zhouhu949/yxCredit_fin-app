package com.zw.rule.activity.service.impl;

import com.zw.base.util.ByxFileUploadUtils;
import com.zw.rule.activity.Activity;
import com.zw.rule.activity.service.ActivityService;
import com.zw.rule.mapper.activity.ActivityMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/30.
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Value("${byx.img.host}")
    private String imgUrl;

    @Value("${byx.img.path}")
    private String imgPath;

    @Value("${byx.img.banner}")
    private String bannerImg;
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
        Activity activity = activityMapper.lookActivity(id);
        String bannerUrl = activity.getActivity_img_url();
        String path = imgUrl.substring(0, imgUrl.lastIndexOf("/")) + bannerUrl.substring(bannerUrl.lastIndexOf("/"));
        ByxFileUploadUtils.deleteFile(path);
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
    public List uploadaBannerImage(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile mfile = multipartRequest.getFile("file");
        String fileName = mfile.getOriginalFilename();
        List imageList = new ArrayList();
        String fileFormat= fileName.substring(fileName.indexOf(".") + 1);
        if ("png".equals(fileFormat) || "jpg".equals(fileFormat) || "jpeg".equals(fileFormat) || "gif".equals(fileFormat) || "".equals(fileFormat)){
            BufferedImage sourceImg= ImageIO.read(mfile.getInputStream());
            Integer imgWidth= sourceImg.getWidth();//750px
            Integer imgHeight=sourceImg.getHeight();//380px
            if (!(imgWidth==750 && imgHeight ==380)){
                //图片大小不符合要求
                imageList.add("-1");
                return imageList;
            }
        }else {
            //图片格式不符合要求
            imageList.add("-2");
            return imageList;
        }
        if(!mfile.isEmpty()){
            String imgURL = ByxFileUploadUtils.uploadFile(imgUrl, mfile.getInputStream(),fileName);
            imageList.add(imgURL);
        }


        return imageList;
    }

}
