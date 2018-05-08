package com.zw.rule.repayment.service;


import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

//专门针对还款的第一个独立Service层
public interface SolvePayMoneyService {
    /**
     * 计算提前结清数据
     * @param crmOrderId
     * @return
     */
    public JSONObject getAdvanceInfo(String crmOrderId);

    public JSONObject getAdvanceInfoV1(String crmOrderId);
}

