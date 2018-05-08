package com.zw.rule.repayment.service;

import com.zw.rule.repayment.po.MagRepaymentExtension;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 功能说明：展期 业务接口
 * @author zh
 */
public interface MagRepaymentExtensionService {
    /**
     * 增加展期
     * @param param 展期信息
     * @return 保存结果
     */
    Map<String, Object> saveExtension(Map<String, Object> param) throws ParseException;

    /**
     * 获取展期详情
     * @param id 展期id
     * @return 展期详情
     */
    MagRepaymentExtension getExtensionDetail(String id);

    /**
     * 获取展期信息
     * @param param 参数
     * @return 展期信息
     */
    List<MagRepaymentExtension> getExtensionList(Map<String, Object> param);

    /**
     * 获取订单展期信息
     * @param param 参数
     * @return 订单展期信息
     */
    List<Map<String, Object>> getOrderExtensions(Map<String, Object> param);

    /**
     * 删除展期信息
     * @param id 展期id
     * @return 删除结果
     */
    int deleteExtension(String id);
}

