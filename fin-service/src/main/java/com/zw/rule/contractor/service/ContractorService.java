package com.zw.rule.contractor.service;

import com.zw.rule.contractor.po.Contractor;
import com.zw.rule.contractor.po.ContractorOrder;
import com.zw.rule.contractor.po.UserVo;
import com.zw.rule.contractor.po.WhiteList;
import com.zw.rule.mybatis.ParamFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ContractorService {
    /**
     * 分页获取总包商信息
     * @param paramFilter
     * @return
     */
    List<Contractor> findContractorList(ParamFilter paramFilter);

    int updateContractor(Contractor contractor) throws Exception;

    int addContractor(Contractor contractor) throws Exception;

    void deleteContractor(List<Long> idList) throws Exception;

    Contractor getByContractorId(String id);

    List<Contractor> selectContractorList() throws Exception;

    /**
     * 分页获取总包商白名单信息
     * @param paramFilter
     * @return
     */
    List findWhiteList(ParamFilter paramFilter) throws Exception;

    /**
     * 更新白名单
     * @param whiteList
     * @return
     */
    int updateWhiteList(WhiteList whiteList) throws Exception;

    /**
     * 添加白名单
     * @param whiteList
     * @return
     */
    int addWhiteList(WhiteList whiteList) throws Exception;

    /**
     * 验证白名单唯一
     * @param map
     * @return
     */
    int vaildateOnly(Map map) throws Exception;

    /**
     * 删除白名单
     * @param idList
     */
    void deleteWhiteList(List<Long> idList) throws Exception;

    /**
     * 根据主键id获取白名单
     * @param id
     * @return
     */
    WhiteList getByWhiteListId(String id) throws Exception;

    /**
     * 分页获取订单汇总信息
     * @param paramFilter
     * @return
     */
    List findContractorOrderList(ParamFilter paramFilter) throws Exception;

    /**
     * 查看订单汇总信息
     * @param id
     * @return
     */
    ContractorOrder getContractorOrder(long id) throws Exception;

   List<UserVo> findUserByMenuUrl(String url);

    //上传资料,返回图片名称
    List uploadContractorImage(HttpServletRequest request) throws Exception ;
}
