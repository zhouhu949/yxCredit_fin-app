package com.zw.rule.repayment.service.impl;

import com.zw.base.util.DateUtils;
import com.zw.rule.SendMessage.service.SendMessageFactory;
import com.zw.rule.SendMessage.service.SendMessageService;
import com.zw.rule.customer.po.Order;
import com.zw.rule.loanmange.po.LoanManage;
import com.zw.rule.mapper.customer.OrderMapper;
import com.zw.rule.mapper.loanmange.LoanManageMapper;
import com.zw.rule.mapper.repayment.MagRepaymentExtensionMapper;
import com.zw.rule.mapper.repayment.MagRepaymentMapper;
import com.zw.rule.repayment.po.MagRepayment;
import com.zw.rule.repayment.po.MagRepaymentExtension;
import com.zw.rule.repayment.service.MagRepaymentExtensionService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 功能说明：展期 业务实现类
 * @author zh
 */
@Service
public class MagRepaymentExtensionServiceImpl implements MagRepaymentExtensionService {
    @Autowired
    private MagRepaymentExtensionMapper magRepaymentExtensionMapper;

    @Autowired
    private MagRepaymentMapper magRepaymentMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SendMessageFactory sendMessageFactory;

    @Resource
    private LoanManageMapper loanManageMapper;

    @Transactional
    @Override
    public Map<String, Object> saveExtension(Map<String, Object> param) throws ParseException{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 0);

        //插入展期
        MagRepaymentExtension extension = new MagRepaymentExtension();
        extension.setId(UUID.randomUUID().toString());
        extension.setExtensionAmount((String)param.get("extension_amount"));
        extension.setExtensionDay(Integer.valueOf((String)param.get("extension_day")));
        extension.setOrderId((String)param.get("order_id"));
        extension.setRepaymentId((String)param.get("repayment_id"));
        extension.setPayCount((String)param.get("pay_count"));
        extension.setOperator((String)param.get("operator"));
        extension.setOperateTime((String)param.get("operate_time"));
        int i = magRepaymentExtensionMapper.insert(extension);
        if (i <= 0)
        {
            map.put("code", 1);
            map.put("msg", "新增展期失败");
            return map;
        }

        //修改本期还款时间
        MagRepayment magRepayment = magRepaymentMapper.selectByPrimaryKey(extension.getRepaymentId());
        String payTime = magRepayment.getPayTime().trim().substring(0, 8);
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.STYLE_3);
        Date payDate = sdf.parse(payTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(payDate);
        cal.add(Calendar.DAY_OF_MONTH, extension.getExtensionDay());
        MagRepayment repayment = new MagRepayment();
        repayment.setId(magRepayment.getId());
        repayment.setPayTime(sdf.format(cal.getTime()));
        int num = magRepaymentMapper.updateByPrimaryKeySelective(repayment);
        if (num <= 0)
        {
            map.put("code", 1);
            map.put("msg", "修改还款时间失败");
            return map;
        }

        //修改订单到期时间
        //查询到期时间
        Map<String, Object> loanparam = new HashMap<String, Object>();
        loanparam.put("orderId", (String)param.get("order_id"));
        List <LoanManage> loans = loanManageMapper.getByPrimaryKey(loanparam);
        if (null != loans && loans.size() > 0 && loans.get(0).getExpirationDate() != null)
        {
            if (Integer.valueOf(repayment.getPayTime()) > Integer.valueOf(loans.get(0).getExpirationDate().substring(0, 8)))
            {
                LoanManage loan = new LoanManage();
                loan.setId(loans.get(0).getId());
                loan.setExpirationDate(repayment.getPayTime());
                loanManageMapper.updateByPrimaryKeySelective(loan);
            }
        }

        //获取订单信息
        Order order = orderMapper.selectByPrimaryKey(extension.getOrderId());
        Integer appType = Integer.valueOf(order.getOrderType());
        Map<String, Object> tempData = new HashMap<String, Object>();
        tempData.put("payCount", extension.getPayCount());
        String pTime = repayment.getPayTime();
        tempData.put("payTime", pTime.substring(0, 4) + "年" + pTime.substring(4, 6) + "月" + pTime.substring(6, 8) + "日");
        //发送消息
        Map<String, Object> sendRes = sendMessageFactory.sendMessage(appType, "1", extension.getOrderId(), tempData);
        if (null == sendRes || 0 != (Integer)sendRes.get("code"))
        {
            map.put("code", 1);
            map.put("msg", "发送消息失败");
        }
        return map;
    }

    @Override
    public MagRepaymentExtension getExtensionDetail(String id) {
        MagRepaymentExtension extension = magRepaymentExtensionMapper.selectById(id);
        return extension;
    }

    @Override
    public List<MagRepaymentExtension> getExtensionList(Map<String, Object> param) {
        List<MagRepaymentExtension> extensionList = magRepaymentExtensionMapper.getExtensionList(param);
        return extensionList;
    }

    @Override
    public List<Map<String, Object>> getOrderExtensions(Map<String, Object> param)
    {
        List<Map<String, Object>> extensions = magRepaymentExtensionMapper.getOrderExtensions(param);
        return extensions;
    }

    @Override
    public int deleteExtension(String id) {
        int i = magRepaymentExtensionMapper.deleteById(id);
        return i;
    }
}

