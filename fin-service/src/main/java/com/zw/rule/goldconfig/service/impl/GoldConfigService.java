package com.zw.rule.goldconfig.service.impl;

import com.zw.rule.goldconfig.service.IGoldConfigService;
import com.zw.rule.mapper.goldConfig.GoldConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年12月20日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) zw.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:zh-pc <br>
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
public class GoldConfigService implements IGoldConfigService {

    @Autowired
    private GoldConfigMapper goldConfigMapper;

    @Override
    public Map getConfig() throws Exception {
        return goldConfigMapper.getConfig();
    }

    @Override
    public void updateConfig(Map params) throws Exception {
        goldConfigMapper.updateConfig(params);
    }
}
