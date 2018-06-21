package com.zw.rule.mapper.system;

import com.alibaba.fastjson.JSONObject;
import com.zw.rule.po.SysDepartment;
import com.zw.rule.po.SysOrganization;

import java.util.List;
import java.util.Map;

/**
 * Created by shengkf on 2017/6/13.
 */
public interface SysDepartmentMapper {
    /**
     * 根据条件查询部门集合
     * @param params
     * @return
     * @throws Exception
     */
    public List<SysDepartment> findDeptByMap(Map params) throws Exception;

    /**
     * 根据部门id查询部门
     * @param params
     * @return
     * @throws Exception
     */
    public List<SysDepartment> findDeptByPid(Map params) throws Exception;

    /**
     * 查询部门
     * @return
     * @throws Exception
     */
    List<SysDepartment> findDeptList();



    /**
     * 添加部门保存
     * @param department
     * @return
     */
    void saveDepartment(SysDepartment department);
    /**
     * 修改部门
     * @param department
     * @return
     */
    void updtaeDepartment(SysDepartment department);

    public List<SysDepartment> findDeptByPids(Long pid) throws Exception;

    void updtaeDepartmentLevel(SysDepartment department);
    SysDepartment findById(long id);


    /**
     * 根据type获取集合
     * @param
     * @return
     */
    List<SysDepartment> getTypList();

    /**
     * 保存公司的注册信息
     * @param map
     * @return
     */
    int addCompanyInfo(Map map);

    /**
     * 获取所有的公司
     * @return
     */
    List<Map> selectAllGongsi(Map map);

    Map findCompany(String company_name);

    int updateCompanyInfo(Map comMap);
//    /**
//     * 更新注册状态
//     */
//    int updateCompanyIsNotRegisterState(Map map);
}
