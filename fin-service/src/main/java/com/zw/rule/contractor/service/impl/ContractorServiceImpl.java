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
import com.zw.rule.mapper.system.SysDepartmentMapper;
import com.zw.rule.mapper.system.UserDao;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.SysDepartment;
import com.zw.rule.po.User;
import com.zw.rule.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    @Resource
    private SysDepartmentMapper sysDepartmentMapper;

    @Resource
    private WhiteListMapper whiteListMapper;

    @Resource
    private UserDao userDao;

    @Value("${byx.img.path}")
    private String imgPath;

    @Value("${byx.img.contractor}")
    private String contractorImg;

    @Override
    public List<UserVo> findUserByMenuUrl(String contractorId) {
        List<Map> contractorList = contractorMapper.findContractorUserList(null);
        List<UserVo> userVolist = contractorMapper.findUserByMenuUrl();
        if(CollectionUtils.isEmpty(contractorList)) {
            return userVolist;
        }
        List<UserVo> newUserVolist = new ArrayList<>();
        StringBuilder stringBuffer = new StringBuilder();
        for(Map map : contractorList) {
            if(StringUtils.isNotBlank(map.get("userId").toString()) && !map.get("contractorId").toString().equals(contractorId)) {
                stringBuffer.append(map.get("userId").toString());
            }
        }
        if(null != userVolist && userVolist.size() > 0) {
            for(UserVo userVo : userVolist) {
                if(StringUtils.isNotBlank(userVo.getUserId())) {
                    for(Map map : contractorList) {
                        if(userVo.getUserId().equals(map.get("userId").toString()) && contractorId.equals(map.get("contractorId").toString())) {
                            userVo.setIsBindUser(true);
                        }
                    }
                    if(!stringBuffer.toString().contains(userVo.getUserId())) {

                        newUserVolist.add(userVo);
                    }
                }
            }
        }
        return newUserVolist;
    }

    /**
     * 总包商下拉框
     * @return
     * @throws Exception
     */
    @Override
    public List<Contractor> selectContractorList(){
        List<Map> mapList = contractorMapper.findContractorUserList(null);
        List<Contractor> list = new ArrayList<>();
        if(!CollectionUtils.isEmpty(mapList)) {
            for(Map map : mapList) {
                Contractor contractor = new Contractor();
                contractor.setId(map.get("contractorId").toString());
                contractor.setUserId(map.get("userId").toString());
                contractor.setContractorName(map.get("contractorName").toString());
                list.add(contractor);
            }
        }
        return list;
    }

    @Override
    public List<Long> findUserPermissByUserId(String userId) {
        User user = userDao.findUnique("getByUserId", userId);
        Long departmentId = user.getOrgId();
        List<SysDepartment> departmentsList = sysDepartmentMapper.findDeptList();



        //根据部门idList查询用户集合

        return null;
    }

    @Override
    public List uploadContractorImage(HttpServletRequest request) throws Exception {
        String fileName="";
        String id = GeneratePrimaryKeyUtils.getUUIDKey();//新的文件名
        //获取根目录
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
        //更新白名单状态
        if(Constants.DISENABLE_STATE.equals(contractor.getState())){
            whiteListMapper.updateStateByContractorId(contractor.getId());
        }
        return contractorMapper.updateByPrimaryKeySelective(contractor);
    }

    @Override
    public int bindContractorUser(Contractor contractor) {
        //删除总包商用户
        contractorMapper.deleteContUser(contractor.getId());
        List<Map> listMap = new ArrayList<>();
        if(StringUtils.isNotBlank(contractor.getUserId())) {
            for(String userId : contractor.getUserId().split(",")) {
                String key = GeneratePrimaryKeyUtils.getUUIDKey();
                Map map = new HashMap();
                map.put("userId",Long.parseLong(userId));
                map.put("contractorId",contractor.getId());
                map.put("id",key);
                listMap.add(map);
            }
        }
        return contractorMapper.insertBatchContUser(listMap);
    }

    /**
     * 验证白名单唯一
     * @return
     */
    @Override
    public List<String> vaildateOnly(Map map) {
        return whiteListMapper.whiteListCountByMap(map);
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
    public int updateWhiteList(WhiteList whiteList) throws Exception {
       return whiteListMapper.updateByPrimaryKeySelective(whiteList);
    }

    @Override
    public int addWhiteList(WhiteList whiteList) throws Exception {
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

    @Override
    public Contractor findByName(String contractorName) {
        return contractorMapper.findByName(contractorName);
    }

    @Override
    public List<String> findALLCards() {
        return contractorMapper.findALLCards();
    }
}
