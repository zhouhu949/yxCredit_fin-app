package com.zw.rule.mapper.process;

import com.zw.rule.mapper.common.BaseMapper;
import com.zw.rule.process.po.Process;

import java.util.List;
import java.util.Map;

/**
 * <strong>Title :<br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月12日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:何浩 <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public interface ProcessMapper extends BaseMapper<Process> {
    List<Process> getProcessByList(Process var1);
    Process getProcessById(Process var1);
    int updateProcess(Process var1);
    int insertProcess(Process var1);
    int insertProcessAndReturnId(Process var1);
    int updateProcessRel(Map map);//流程配置更新关联产品或商品信息
    int updateStateForSysCodeTable(String code);
}

