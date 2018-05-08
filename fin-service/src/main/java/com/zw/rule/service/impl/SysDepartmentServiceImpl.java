package com.zw.rule.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zw.base.util.ChkUtil;
import com.zw.rule.mapper.system.SysDepartmentMapper;
import com.zw.rule.po.SysDepartment;
import com.zw.rule.service.SysDepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by shengkf on 2017/6/13.
 */
@Service
public class SysDepartmentServiceImpl implements SysDepartmentService {
    @Resource
    private SysDepartmentMapper sysDepartmentMapper;

    /**
     * 功能说明：根据条件查询组织架构的公司部门
     * @param paramJson
     * @return
     * @throws Exception
     */
    public List findAllDepts(Map paramJson)throws Exception {
        List<SysDepartment> list=sysDepartmentMapper.findDeptByMap(paramJson);
        return list;
    }

    /**
     * 添加公司、部门保存
     * @param department
     * @return
     */
    public boolean saveDepartment(SysDepartment department) throws Exception {
        Map map=new HashMap();
        map.put("id",department.getPid());//部门id
        //List<SysDepartment> list=sysDepartmentMapper.findDesptByPid(map);//根据部门id查询字节部门节点的个数
        List<SysDepartment> list2=sysDepartmentMapper.findDeptByMap(map);
        String level="0";
        if(list2.size()!=0){//不为空查到父级的层级level
            level=list2.get(0).getLevel();
        }
        department.setLevel(String.valueOf(Integer.parseInt(level)+1));
      /*  //department.setSequence(String.valueOf(list.size()+1));//生成序列号
        String uuid = UUID.randomUUID().toString();
        department.setId(uuid);*/
        department.setStatus(1);
        sysDepartmentMapper.saveDepartment(department);
        return true;
    }
    /**
     * 修改公司、部门
     * @param department
     * @return
     */
    public boolean updateDepartment(SysDepartment department) throws Exception {
        Map map=new HashMap();
        map.put("id",department.getPid());//部门id
        List<SysDepartment> list2=sysDepartmentMapper.findDeptByMap(map);
        String level="0";
        if(list2.size()!=0){//不为空查到父级的层级level
            level=list2.get(0).getLevel();
        }
        List<SysDepartment> list=sysDepartmentMapper.findDeptByPids(department.getId());
        int departmentlevel=Integer.parseInt(department.getLevel());
        int count =departmentlevel-(Integer.parseInt(level)+1);

        for(int i=0;i<list.size();i++){
            String level_child=list.get(i).getLevel();
            int level_new =Integer.parseInt(level_child)-count;
            list.get(i).setLevel(String.valueOf(level_new));
            sysDepartmentMapper.updtaeDepartmentLevel(list.get(i));
        }
        department.setLevel(String.valueOf(Integer.parseInt(level)+1));
        sysDepartmentMapper.updtaeDepartment(department);
        return true;
    }

    @Override
    public SysDepartment findById(long orgId) {
        return sysDepartmentMapper.findById(orgId);
    }

    @Override
    public List<SysDepartment> getTypList() {
        return sysDepartmentMapper.getTypList();
    }

    @Override
    public int addCompanyInfo(Map map) {
        return sysDepartmentMapper.addCompanyInfo(map);
    }

    /**
     * 获取所有的公司
     * @param map
     * @return
     */
    public List<Map> getAllGongsi(Map map){

        return sysDepartmentMapper.selectAllGongsi(map);
    }

    @Override
    public Map findCompany(String company_name) {
        return sysDepartmentMapper.findCompany(company_name);
    }

    @Override
    public int updateCompanyInfo(Map comMap) {
        return sysDepartmentMapper.updateCompanyInfo(comMap);
    }

    /**
     * 更新是否已注册状态
     * @param map
     * @return
     */
//    public int updateCompanyIsNotRegisterState(Map map){
//        return sysDepartmentMapper.updateCompanyIsNotRegisterState(map);
//    }
}
