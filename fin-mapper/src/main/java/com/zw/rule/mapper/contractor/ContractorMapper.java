package com.zw.rule.mapper.contractor;

import com.zw.rule.contractor.po.Contractor;
import com.zw.rule.contractor.po.ContractorOrder;
import com.zw.rule.contractor.po.UserVo;
import com.zw.rule.mybatis.ParamFilter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * AppMessageMapper数据库操作接口类
 * 
 **/

public interface ContractorMapper{
    List<Contractor> findContractorList(ParamFilter paramFilter);

    Contractor selectByPrimaryKey(String id);

    List<UserVo> findUserByMenuUrl();

    List<Contractor> selectContractorList(@Param("state") String state);

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

    List<ContractorOrder> findContractorOrderList(ParamFilter paramFilter);

    ContractorOrder getContractorOrder(@Param("id") Long value);
}