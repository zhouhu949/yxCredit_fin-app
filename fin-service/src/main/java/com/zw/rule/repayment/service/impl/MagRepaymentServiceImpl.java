package com.zw.rule.repayment.service.impl;

import com.zw.rule.mapper.repayment.MagRepaymentMapper;
import com.zw.rule.repayment.po.MagRepayment;
import com.zw.rule.repayment.service.MagRepaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年11月17日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:yaoxuetao <br>
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
public class MagRepaymentServiceImpl implements MagRepaymentService {
    @Autowired
    private MagRepaymentMapper magRepaymentMapper;
    @Override
    public List<MagRepayment> getRepayment(String id) {
        return magRepaymentMapper.getRepayment(id);
    }
}

