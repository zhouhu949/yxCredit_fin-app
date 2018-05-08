package com.zw.rule.red.service.impl;

import com.zw.rule.mapper.product.RedMapper;
import com.zw.rule.product.Red;
import com.zw.rule.product.RedInfo;
import com.zw.rule.red.service.RedService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/17 0017.
 */
@Service
public class IRedServiceImpl implements RedService {
    @Resource
    public RedMapper redMapper;
    @Override
    public List getRedList(Red red) {
        return redMapper.getRedList(red);
    }

    @Override
    public int addRed(Red red) {
        return redMapper.addRed(red);
    }

    @Override
    public int updateRed(Red red) {
        return redMapper.updateRed(red);
    }

    @Override
    public List<Red>  getList(Map map){
        return redMapper.getList(map);
    }

    @Override
    public List<RedInfo> getRedInfoList(Map map) {
        return redMapper.getRedInfoList(map);
    }
}
