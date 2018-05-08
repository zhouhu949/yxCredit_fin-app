package com.zw.rule.reclaimMerchant.service.impl;

import com.zw.rule.mapper.ReclaimMerchant.ReclaimMerchantMapper;
import com.zw.rule.reclaimMerchant.ReclaimMerchant;
import com.zw.rule.reclaimMerchant.service.ReclaimMerchantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by liuxiaolong on 2017/12/20.
 */
@Service
public class ReclaimMerchantServiceImpl implements ReclaimMerchantService {
    @Resource
    private ReclaimMerchantMapper  mapper;

    @Override
    public List<ReclaimMerchant> getMerchantList(Map map) {
        return mapper.getMerchantList(map);
    }

    @Override
    public int addMerchant(ReclaimMerchant merchant) {

        return mapper.addMerchant(merchant);
    }

    @Override
    public int updateMerchant(ReclaimMerchant merchant) {
        return mapper.updateMerchant(merchant);
    }


    @Override
    public ReclaimMerchant getById(String id) {
        return mapper.getById(id);
    }

    @Override
    public int updateStatus(ReclaimMerchant merchant) {
        return mapper.updateStatus(merchant);
    }

    @Override
    public int deleteMerchant(String id) {
        return mapper.deleteMerchant(id);
    }



    @Override
    public List<ReclaimMerchant> getMerchantListByDepId(Map depId) {
        return mapper.getMerchantListByDepId(depId);
    }


}
