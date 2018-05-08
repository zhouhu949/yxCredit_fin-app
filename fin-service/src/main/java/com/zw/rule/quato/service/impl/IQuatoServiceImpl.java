package com.zw.rule.quato.service.impl;

import com.zw.rule.mapper.product.ProYieldMapper;
import com.zw.rule.product.ProYield;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/17 0017.
 */
@Service
public class IQuatoServiceImpl implements com.zw.rule.quato.service.impl.IQuatoService {
    @Resource
    public ProYieldMapper proYieldMapper;
    @Override
    public List getQuato(ProYield proYield) {
        return proYieldMapper.getQuato(proYield);
    }

    @Override
    public List getQuatoList() {
        return proYieldMapper.getQuatoList();
    }

    @Override
    public int addQuato(ProYield proYield) {
        return proYieldMapper.addQuato(proYield);
    }

    @Override
    public int updateQuato(ProYield proYield) {
        return proYieldMapper.updateQuato(proYield);
    }

    @Override
    public List<ProYield>  getList(Map map){
        return proYieldMapper.getList(map);
    }
}
