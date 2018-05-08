//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


package com.zw.rule.upload.service.impl;


import com.zw.UploadFile;
import com.zw.rule.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {

    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.bucket}")
    private String bucket;

    @Override
    public Map uploadFile(HttpServletRequest request,String url)throws Exception{
        Map map = new HashMap();
        String fileName="";
        String id = UUID.randomUUID().toString();
        //获取根目录
        String root = request.getSession().getServletContext().getRealPath("/fintecher_file");
        //捕获前台传来的图片，并用uuid命名，防止重复
        Map<String, Object> allMap = UploadFile.getFile(request,root+ File.separator + url, id);
        List<Map<String, Object>> list = (List<Map<String, Object>>) allMap.get("fileList");
        String originalName = "";
        //当前台有文件时，给图片名称变量赋值
        if (!list.isEmpty()) {
            Map<String, Object> fileMap = list.get(0);
            fileName = "/fintecher_file/"+url+"/"+(String) fileMap.get("Name");
            originalName = (String) fileMap.get("originalName");
        }
        if(!fileName.equals("")){
            map.put("msg","上传成功！");
            map.put("url",fileName);
            map.put("originalName",originalName);
            map.put("code","0");
        }else{
            map.put("msg","上传失败！");
            map.put("code","1");
        }
        return map;
    }

    @Override
    public void deleteImage(HttpServletRequest request, String fileName) {
        //图片路径及名称
        String filePath = request.getSession().getServletContext().getRealPath("/") + fileName;
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
    }
    @Override
    public Map uploadFileContract(HttpServletRequest request, String url) throws Exception {
        Map map = new HashMap();
        String fileName="";
        String id = UUID.randomUUID().toString();
        //获取根目录
        String root = request.getSession().getServletContext().getRealPath("/fintecher_file");
        //捕获前台传来的图片，并用uuid命名，防止重复
        Map<String, Object> allMap = UploadFile.getFileContract(request,root+ File.separator + url, id);
        List<Map<String, Object>> list = (List<Map<String, Object>>) allMap.get("fileList");
        String originalName = "";
        //当前台有文件时，给图片名称变量赋值
        if (!list.isEmpty()) {
            Map<String, Object> fileMap = list.get(0);
            fileName = "/fintecher_file/"+url+"/"+(String) fileMap.get("Name");
            if(!fileName.equals("")){
                map.put("msg","上传成功！");
                map.put("url",fileName);
                map.put("originalName",fileMap.get("originalName"));
                map.put("file", fileMap.get("file"));
                map.put("code","0");
            }else{
                map.put("msg","上传失败！");
                map.put("code","1");
            }
        }
        return map;
    }


    @Override
    public Map uploadFileOSS(HttpServletRequest request,String url)throws Exception{
        Map map = new HashMap();
        String fileName="";
        String id = UUID.randomUUID().toString();
        String catalog="fintecher_file"+"/"+url+"/";
        //获取根目录
        String root = request.getSession().getServletContext().getRealPath("/fintecher_file");
        //捕获前台传来的图片，并用uuid命名，防止重复
        //Map<String, Object> allMap = UploadFile.getFile(request,root+ File.separator + url, id);
        Map<String, Object> allMap = UploadFile.getFileOSS(request,root+ File.separator + url, id,catalog,bucket,endpoint,accessKeyId,accessKeySecret);
        List<Map<String, Object>> list = (List<Map<String, Object>>) allMap.get("fileList");
        String originalName = "";
        //当前台有文件时，给图片名称变量赋值
        if (!list.isEmpty()) {
            Map<String, Object> fileMap = list.get(0);
            fileName = (String) fileMap.get("Name");
            originalName = (String) fileMap.get("originalName");
        }
        if(!fileName.equals("")){
            map.put("msg","上传成功！");
            map.put("url",fileName);
            map.put("originalName",originalName);
            map.put("type",allMap.get("type"));
            map.put("businessType",allMap.get("businessType"));
            map.put("id",allMap.get("id"));//客户id
            map.put("orderId",allMap.get("orderId"));//订单id
            map.put("merId",allMap.get("merId"));//商户id
            map.put("code","0");
        }else{
            map.put("msg","上传失败！");
            map.put("code","1");
        }
        return map;
    }
    public String getOSS(String path,String name)throws Exception{
        return UploadFile.getOSS(name,"fintecher_file"+"/pdf/",bucket,endpoint,accessKeyId,accessKeySecret,path);
    }
    //删除OSS文件(全路径)
    public Map deleteFilePathOSS(String url) throws Exception{
        Map map = new HashMap();
        //截取域名后的路径
        String pathkey = url.substring(url.lastIndexOf(".com/")+5);
        if (UploadFile.deleteFilePathOSS(pathkey,bucket,endpoint,accessKeyId,accessKeySecret)){
            map.put("msg","删除成功！");
            map.put("code","0");
        }else {
            map.put("msg","删除失败！");
            map.put("code","1");
        }
        return  map;
    }

}
