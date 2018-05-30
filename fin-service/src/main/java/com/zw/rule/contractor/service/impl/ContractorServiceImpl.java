package com.zw.rule.contractor.service.impl;

import com.google.common.base.Strings;
import com.zw.UploadFile;
import com.zw.base.util.GeneratePrimaryKeyUtils;
import com.zw.rule.contractor.po.Contractor;
import com.zw.rule.contractor.po.ContractorOrder;
import com.zw.rule.contractor.po.UserVo;
import com.zw.rule.contractor.po.WhiteList;
import com.zw.rule.contractor.service.ContractorService;
import com.zw.rule.mapper.contractor.ContractorMapper;
import com.zw.rule.mapper.contractor.WhiteListMapper;
import com.zw.rule.mybatis.ParamFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class ContractorServiceImpl implements ContractorService {

    @Resource
    private ContractorMapper contractorMapper;

    @Value("${byx.img.path}")
    private String imgPath;

    @Value("${byx.img.contractor}")
    private String contractorImg;

    @Override
    public List<UserVo> findUserByMenuUrl(String contractorId) {
        List<Contractor> contractorList = contractorMapper.selectContractorList();
        List<UserVo> userVolist = contractorMapper.findUserByMenuUrl();
        if(null == contractorList || contractorList.size() == 0) {
            return userVolist;
        }
        List<UserVo> newUserVolist = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        for(Contractor contractor : contractorList) {
            if(StringUtils.isNotBlank(contractor.getUserId()) && !contractor.getId().equals(contractorId)) {
                stringBuffer.append(contractor.getUserId());
            }
        }
        if(null != userVolist && userVolist.size() > 0) {
            for(UserVo userVo : userVolist) {
                if(StringUtils.isNotBlank(userVo.getUserId()) && !stringBuffer.toString().contains(userVo.getUserId())) {
                    newUserVolist.add(userVo);
                }
            }
        }
        return newUserVolist;
    }

    @Resource
    private WhiteListMapper whiteListMapper;

    @Override
    public List<Contractor> selectContractorList() throws Exception {
        return contractorMapper.selectContractorList();
    }

    @Override
    public List uploadContractorImage(HttpServletRequest request) throws Exception {
        String fileName="";
        String id = GeneratePrimaryKeyUtils.getUUIDKey();//新的文件名
        //获取根目录
        //String root = request.getSession().getServletContext().getRealPath("/");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String currentDateStr = format.format(new Date());
        String newFilePath = imgPath + File.separator + contractorImg + currentDateStr;//文件保存路径url
        Map<String, Object> allMap = UploadFile.getFile(request, newFilePath, id);
        List<Map<String, Object>> list = (List<Map<String, Object>>) allMap.get("fileList");
        //当前台有文件时，给图片名称变量赋值
        if (!list.isEmpty()) {
            Map<String, Object> fileMap = list.get(0);
            fileName = contractorImg + currentDateStr+"/"+ fileMap.get("Name").toString();
    }
        List imageList = new ArrayList();
        imageList.add(fileName);
        return imageList;
    }




    @Override
    public List<Contractor> findContractorList(ParamFilter paramFilter){
        return contractorMapper.findContractorList(paramFilter);
    }

    @Override
    public int updateContractor(Contractor contractor){
        return contractorMapper.updateByPrimaryKeySelective(contractor);
    }

    @Override
    public int addContractor(Contractor contractor){
        checkNotNull(contractor, "总包商信息不能为空");
        checkArgument(!Strings.isNullOrEmpty(contractor.getContractorName()), "总包商名不能为空");
        //checkArgument(RegexUtil.isMobile(user.getTel()), "手机号码格式不正确");
        //checkArgument(RegexUtil.isEmail(user.getEmail()), "邮箱格式不正确");

        String uuid = UUID.randomUUID().toString();
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        contractor.setAdmissionDate(sdf.format(new Date()));
        contractor.setId(uuid);
        return contractorMapper.insertSelective(contractor);
    }

    @Override
    public void deleteContractor(List<Long> idList) {

    }

    @Override
    public Contractor getByContractorId(String id){
        return contractorMapper.selectByPrimaryKey(id);
    }

    @Override
    public List findWhiteList(ParamFilter paramFilter){
        return whiteListMapper.findWhiteList(paramFilter);
    }

    @Override
    public int updateWhiteList(WhiteList whiteList) {
       return whiteListMapper.updateByPrimaryKeySelective(whiteList);
    }

    @Override
    public int addWhiteList(WhiteList whiteList){
        String uuid = GeneratePrimaryKeyUtils.getUUIDKey();
        whiteList.setId(uuid);
        return  whiteListMapper.insertSelective(whiteList);
    }

    @Override
    public void deleteWhiteList(List<Long> idList) {

    }

    @Override
    public WhiteList getByWhiteListId(String id){
        return whiteListMapper.selectByPrimaryKey(id);
    }

    @Override
    public List findContractorOrderList(ParamFilter paramFilter) {
        return contractorMapper.findContractorOrderList(paramFilter);
    }

    @Override
    public ContractorOrder getContractorOrder(long id){
        return contractorMapper.getContractorOrder(id);
    }
}
