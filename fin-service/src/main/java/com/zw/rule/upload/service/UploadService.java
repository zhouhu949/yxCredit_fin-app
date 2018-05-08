package com.zw.rule.upload.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface UploadService  {

    Map uploadFile(HttpServletRequest request, String url) throws Exception;
    //删除服务器图片
    void deleteImage(HttpServletRequest request, String fileName);
    //上传能返回有后缀名
    Map uploadFileContract(HttpServletRequest request, String url) throws Exception;
    //上传文件到OSS
    Map uploadFileOSS(HttpServletRequest request, String url) throws Exception;
    //删除OSS文件(全路径)
    Map deleteFilePathOSS(String url) throws Exception;
    //文件直接上传oss
    String getOSS(String path, String name)throws Exception;

}

