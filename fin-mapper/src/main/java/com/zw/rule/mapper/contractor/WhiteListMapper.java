package com.zw.rule.mapper.contractor;

import com.zw.rule.contractor.po.WhiteList;
import com.zw.rule.customer.po.AppMessage;
import com.zw.rule.mapper.common.BaseMapper;
import com.zw.rule.mybatis.ParamFilter;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * AppMessageMapper数据库操作接口类
 * 
 **/

public interface WhiteListMapper extends BaseMapper<WhiteList> {
    List<WhiteList> findWhiteList(ParamFilter paramFilter);

    WhiteList selectByPrimaryKey(String id);

    /**
     * 更新白名单状态（匹配有值的字段）
     * @param contractorId
     * @return
     */
    int updateStateByContractorId(@Param("contractorId") String contractorId);
}