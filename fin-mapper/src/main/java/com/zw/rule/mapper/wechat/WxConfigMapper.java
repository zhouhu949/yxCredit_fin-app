package com.zw.rule.mapper.wechat;

import com.zw.rule.wechat.WxConfig;

public interface WxConfigMapper {

    void update(WxConfig wxConfig);

    void insert(WxConfig wxConfig);

    WxConfig selectParam();
}
