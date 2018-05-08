package com.zw.rule.service;


import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.Safe;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13 0013.
 */
public interface SafeService {
    public void insert(String name,String confName);

    public String selectErrorCount(String confName);

    public List<Safe> getSafeList(ParamFilter param);
}
