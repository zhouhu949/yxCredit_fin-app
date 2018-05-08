package com.zw.rule.mapper.customer;

import com.zw.rule.customer.po.FollowUp;

import java.util.List;
import java.util.Map;

public interface FollowUpMapper {
    int deleteByPrimaryKey(String id);

    int insert(FollowUp record);

    int insertSelective(FollowUp record);

    FollowUp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FollowUp record);

    int updateByPrimaryKey(FollowUp record);

    List<FollowUp> getFollowUp(Map map);
}