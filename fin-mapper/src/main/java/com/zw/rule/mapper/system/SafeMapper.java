package com.zw.rule.mapper.system;

import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.Safe;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13 0013.
 */
public interface SafeMapper {
    int deleteByPrimaryKey(String id);

    int insert(Safe record);

    int insertSelective(Safe record);

    Safe selectByPrimaryKey(String id);

    Safe selectByConfName(String confName);

    int updateByPrimaryKeySelective(Safe record);

    int updateByPrimaryKey(Safe record);

    List<Safe> getSafeList(ParamFilter param);

    int getCount(ParamFilter param);
}
