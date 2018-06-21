package com.zw.rule.mapper.contractor;

import com.zw.rule.contractor.po.Contractor;
import com.zw.rule.contractor.po.ContractorOrder;
import com.zw.rule.contractor.po.UserVo;
import com.zw.rule.mybatis.ParamFilter;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * AppMessageMapper数据库操作接口类
 * 
 **/

public interface ContractorMapper{
    List<Contractor> findContractorList(ParamFilter paramFilter);

    Contractor selectByPrimaryKey(String id);

    List<UserVo> findUserByMenuUrl();

    //获取所有已经绑定总包商的用户
    List<Map> findContractorUserList(List<Long> idList);

    List<Contractor> selectContractorList(@Param("state") String state);

    /**
     * 根据组织机构idList获取用户
     * @param idList
     * @return
     */
    List<Long> findUserListByPid(@Param("idList") List<Long> idList);

    /**
     * 添加 （匹配有值的字段）
     * @param contractor
     * @return
     */
    int insertSelective(Contractor contractor);

    /**
     * 更新（匹配有值的字段）
     * @param contractor
     * @return
     */
    int updateByPrimaryKeySelective(Contractor contractor);

    /**
     * 删除总包商用户
     * @param contractorId
     */
    void deleteContUser(String contractorId);

    /**
     * 批量插入总包商用户信息
     * @param listMap
     */
    int insertBatchContUser(List<Map> listMap);

    List<ContractorOrder> findContractorOrderList(ParamFilter paramFilter);

    ContractorOrder getContractorOrder(@Param("id") Long value);

    /**
     * 按contractor_name 查询总包商 create by 陈淸玉
     * @param contractorName 总包商名称
     * @return 总包商信息
     */
    Contractor findByName(@Param("contractorName") String contractorName);

    /**
     * 查询所有的身份证号码 create by 陈淸玉
     * @return 身份证号列表
     */
    List<String> findALLCards();
}