package com.zw.rule.service.impl;

import com.zw.rule.mapper.system.SafeMapper;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.po.Safe;
import com.zw.rule.service.SafeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月13日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Administrator <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
@Service
public class SafeServiceImpl implements SafeService{
    @Resource
    private SafeMapper safeMapper;

    /**
     * 安全设置添加
     * @param name
     * @param confName
     */
    public void insert(String name,String confName){
        Safe safe = safeMapper.selectByConfName(confName);
        Date time = new Date();
        if(safe==null){
            String id  = UUID.randomUUID().toString();
            safe.setId(id);
            safe.setName(name);
            safe.setCode("0");
            safe.setConfName(confName);
            safe.setDescription("");
            safe.setCreatTime(time);
            safe.setAlterTime(time);
            safeMapper.insert(safe);
        }else{
            safe.setName(name);
            safe.setAlterTime(time);
            safeMapper.updateByPrimaryKey(safe);
        }
    }

    /**
     * 根据配置名称查询值
     * @param confName
     * @return
     */
    public String selectErrorCount(String confName){
        Safe safe = safeMapper.selectByConfName(confName);
        if(safe!=null){
            String name = safe.getName();
            return name;
        }
        return null;
    };

    public List<Safe> getSafeList(ParamFilter param){
        List<Safe> list = safeMapper.getSafeList(param);
        int count = safeMapper.getCount(param);
        if(param.getPage() != null){
            param.getPage().setResultCount(count);
        }
        return list;
    }
}