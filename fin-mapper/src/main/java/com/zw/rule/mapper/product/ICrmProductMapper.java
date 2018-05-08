package com.zw.rule.mapper.product;

import com.zw.rule.product.Fee;
import com.zw.rule.product.ProCrmProduct;
import com.zw.rule.product.WorkingProductDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 功能说明：信贷类型/产品表Dao层接口
 * @author weiyingni 2015-09-07
 * 修改人: 
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：
 * Copyright zzl-apt
 */
public interface ICrmProductMapper{
	
	/**
	 * 功能说明：查找产品系类
	 * wangmin  2017-6-13
	 * @return
	 */
	 List getProductSeries();

	/**
	 * 功能说明：根据产品系列，展示对应列表
	 * wangmin  2017-6-13
	 * @return
	 */
	 List getProductType(String id);

	/**
	 * 功能说明：展示产品类型详情
	 * wangmin  2017-6-13
	 * @return
	 */
	 List getTypeDetail(String id);

	/**
	 * 功能说明：根据产品类型，展示产品详情
	 * wangmin  2017-6-13
	 * @return
	 */
	 List getProductDetail(String crmProductId);

	/**
	 * 功能说明：获取 查找产品系类的数量
	 * @author wangmin
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * date: 2017-6-13
	 * @throws Exception
	 */
	int countCrmProduct() throws Exception;

	/**
	 * 功能说明：停用产品系列或者类型
	 * @author wangmin
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * date: 2017-6-15
	 * @throws Exception
	 */
	int updateStatus(@Param("id")String id,@Param("status")int status) throws Exception;

    /**
     * 功能说明：停用具体产品
     * @author wangmin
     * @throws  该方法可能抛出的异常，异常的类型、含义。
     * date: 2017-6-15
     * @throws Exception
     */
    int updateProductStatus(@Param("id")String id, @Param("status")int status) throws Exception;

    /**
     * 功能说明：查询当前系列下已启用的产品类型数量
     * @author wangmin
     * @throws  该方法可能抛出的异常，异常的类型、含义。
     * date: 2017-6-15
     * @throws Exception
     */
    int countAbleType(String id) throws Exception;

    /**
     * 功能说明：查询当前类型下已发布的产品数量
     * @author wangmin
     * @throws  该方法可能抛出的异常，异常的类型、含义。
     * date: 2017-6-15
     * @throws Exception
     */
    int countPublisProduct(String id) throws Exception;

    /**
     * 功能说明：查询当前类型下已发布的产品数量
     * @author wangmin
     * @throws  该方法可能抛出的异常，异常的类型、含义。
     * date: 2017-6-15
     * @throws Exception
     */
    int addProductSeries(ProCrmProduct proCrmProduct) throws Exception;

	/**
	 * 功能说明：修改
	 * @author 吕彬
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * date: 2017-6-15
	 * @throws Exception
	 */
	int updateProductSeriesById(ProCrmProduct proCrmProduct) throws Exception;

    /**
     * 功能说明：修改
     * @author wangmin
     * @throws  该方法可能抛出的异常，异常的类型、含义。
     * date: 2017-6-15
     * @throws Exception
     */
    List getLastNumber() throws Exception;

	/**
	 * 功能说明：显示具体产品详情
	 * wangmin  2017-6-16
	 * @return
	 */
	 List getProductInfo(String id);

	/**
	 * 功能说明：新增产品第三级 期数费率
	 * @author lvbin
	 * @throws 
	 * date: 2017-6-15
	 * @throws Exception
	 */
	int insertProductDetail(WorkingProductDetail workingProductDetail) throws Exception;
	/**
	 * 功能说明：修改产品第三级 期数费率
	 * @author lvbin
	 * @throws 
	 * date: 2017-6-15
	 * @throws Exception
	 */
	int updateProductDetail(WorkingProductDetail workingProductDetail) throws Exception;

	/**
	 * 功能说明：停用具体产品
	 * @author wangmin
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * date: 2017-6-15
	 * @throws Exception
	 */
	List<Map> selectProduct(@Param("proName")String proName, @Param("SeriesId")String SeriesId) throws Exception;

	List<Map> bgEfOrder(String orderId);

	List<Map> getRepayDetail(String orderId);

	//获取产品分期集合
	List<Map> getProductList (Map map);

	//获取产品费率
	List<Map> getFeeList (Fee fee);

	int delFee(String id);

	//修改产品费率
	int updateFee (Fee fee);

	List<Fee> isExist(Fee fee);

	//添加产品费率
	int addFee (Fee fee);

	//获取所有产品
	List getProduct();

	//获取所有子产品
	List getProductDetailSelect();

	//获取产品下所有分期
	List getPeriodsList(String crmProductId);
}
