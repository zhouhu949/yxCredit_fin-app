package com.zw.rule.product.service;


import com.alibaba.fastjson.JSONObject;
import com.zw.rule.product.Fee;
import com.zw.rule.product.ProCrmProduct;
import com.zw.rule.product.WorkingProductDetail;

import java.util.List;
import java.util.Map;

/**
 *
 * 功能说明：信贷类型/产品表service层接口
 * @author wangmin 2017-06-13
 * 修改人:
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：
 */
public interface ICrmProductService {

    /**
     * 功能说明：得到产品系列列表
     * wangmin  2017-6-13
     * @return
     */
    public JSONObject getProductSeries() throws Exception;

    /**
     *
     * 功能说明：getProductType 获取对应系列中的产品类型
     * wangmin  2017-6-13
     * @param id 父id
     * @param signature 验签标识
     * @return
     * 最后修改时间：2017-6-14
     * 修改人：
     * 修改内容：
     * 修改注意点：
     * @throws Exception
     */
    public JSONObject getProductType(String id) throws Exception;

    /**
     * 功能说明：得到产品类型详情
     * wangmin  2017-6-13
     * @return
     */
    public JSONObject getTypeDetail(String crmProductId) throws Exception;

    /**
     * 功能说明：得到产品系列列表
     * wangmin  2017-6-13
     * @return
     */
    public JSONObject getProductDetail(String crmProductId) throws Exception;

    /**
     * 功能说明：停用产品系列或类型
     * wangmin  2017-6-15
     * @return
     */
    public int updateStatus(String id,int status) throws Exception;

    /**
     * 功能说明：停用具体产品
     * wangmin  2017-6-17
     * @return
     */
    public int updateProductStatus(String id,int status) throws Exception;
    /**
     * 功能说明：查询产品系列下已启用的产品类型数量
     * wangmin  2017-6-15
     * @return
     */
    public int countAbleType(String id) throws Exception;

    /**
     * 功能说明：查询产品系列下已启用的产品类型数量
     * wangmin  2017-6-15
     * @return
     */
    public int countPublisProduct(String id) throws Exception;

    /**
     * 功能说明：增加产品系列
     * wangmin  2017-6-15
     * @return
     */
    public int addProductSeries(ProCrmProduct proCrmProduct) throws Exception;

    /**
     * 功能说明：修改产品系列
     * wangmin  2017-6-15
     * @return
     */
    public int updateProductSeries(ProCrmProduct proCrmProduct) throws Exception;

    /**
     * 功能说明：查看最后一个编号
     * wangmin  2017-6-15
     * @return
     */
    public String getNumber() throws Exception;

    /**
     * 功能说明：得到具体产品的所有信息
     * wangmin  2017-6-13
     * @return
     */
    public JSONObject getProductInfo(String id) throws Exception;

    /**
     * 功能说明：新增产品第三级 期数费率
     * @author lvbin
     * @throws 
     * date: 2017-6-15
     * @throws Exception
     */
    public int addOrUpdateProductDetail(WorkingProductDetail workingProductDetail,String flag) throws Exception;

    /**
     * 功能说明：查询产品
     * wangmin  2017-6-13
     * @return
     */
    public JSONObject selectProduct(String proName,String SeriesId) throws Exception;

    /**
     * 功能说明：查询费率
     * gaozhidong  2017-7-17
     * @return
     */
    List  getFeeList(Fee fee);

    int delFee(String id);

    Fee getMagFeeList(Map map);

    /**
     * 功能说明：修改费率
     * gaozhidong  2017-7-17
     * @return
     */
    int updateFee(Fee fee);

    List<Fee> isExist(Fee fee);


    /**
     * 功能说明：添加费率
     * gaozhidong  2017-7-17
     * @return
     */
    int addFee(Fee fee);

    /**
     * 功能说明：获取产品列表
     * gaozhidong  2017-7-17
     * @return
     */
    JSONObject getProduct();

    JSONObject getProductDetailSelect();

    /**
     * 功能说明：获取产品分期列表
     * gaozhidong  2017-7-17
     * @return
     */
    JSONObject getPeriodsList(String crmProductId);
}

