package com.zw.rule.mapper.product;

import com.zw.rule.mapper.product.ProCrmProductMapper;
import com.zw.rule.product.Fee;
import com.zw.rule.product.ProCrmProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProCrmProductMapper {
    /**
     * 功能说明：查找产品系类
     * huangyuanlong  2016-6-27
     * @return
     */
    List getProductSeries();
    int deleteByPrimaryKey(String id);

    int insert(ProCrmProduct record);

    int insertSelective(ProCrmProduct record);

    ProCrmProduct selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProCrmProduct record);

    int updateByPrimaryKey(ProCrmProduct record);

    //添加流程时绑定产品
    List<ProCrmProduct> getProcessByList(@Param("searchKey") String searchKey);

    List<ProCrmProduct> getProductByList(Map map);

    List<Map> getPayProductInfo(String crmOrderId);

    List<Map<String,String>> getProductList();

    Map getFee(Fee fee);
}