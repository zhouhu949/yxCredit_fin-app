package com.zw.rule.service;

import com.zw.rule.po.SysDepartment;

import java.util.List;
import java.util.Map;

/**
 * Created by shengkf on 2017/6/13.
 */
public interface SysDepartmentService {
    /**
     * 根据条件查询组织架构的公司部门
     * @param map
     * @return
     * @throws Exception
     */
    public List findAllDepts(Map map)throws Exception;

    /**
     * 添加公司、部门保存
     * @param department
     * @return
     * @throws Exception
     */
    public boolean saveDepartment(SysDepartment department)throws Exception;

    /**
     * 修改公司、部门
     * @param department
     * @return
     * @throws Exception
     */
    public boolean updateDepartment(SysDepartment department)throws Exception;


    SysDepartment findById(long orgId);


    List<SysDepartment> getTypList();

    int addCompanyInfo(Map map);
    //获取所有的公司
    List<Map> getAllGongsi(Map map);

    Map findCompany(String company_name);

    int updateCompanyInfo(Map comMap);
    //更改公司是否已注册状态
//    int updateCompanyIsNotRegisterState(Map map);
}
