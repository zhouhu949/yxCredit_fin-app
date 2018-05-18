package com.zw.rule.product.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zw.base.util.MsgConsts;
import com.zw.rule.mapper.product.FeeMapper;
import com.zw.rule.mapper.product.ICrmProductMapper;
import com.zw.rule.product.Fee;
import com.zw.rule.product.ProCrmProduct;
import com.zw.rule.product.WorkingProductDetail;
import com.zw.rule.product.service.ICrmProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 功能说明：信贷产品service层
 * @author wangmin
 * 修改人:
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：2017-06-13
 */
@Service
public class CrmProductServiceImpl implements ICrmProductService {


    // 信贷产品Dao
    @Resource
    public ICrmProductMapper crmProductMapper;

    @Resource
    public FeeMapper feeMapper;
/*
    // V2信贷产品系数
    @Resource
    private ICrmProductDetailDao crmProductDetailDao;

    // V1信贷产品系数
    @Resource
    private ILowerProductDao lowerProductDao;

    //V3信贷产品系数
    @Resource
    private IWorkingProductDetailDao workingProductDetailDao;

    //V3信贷产品期数公司利率
    @Resource
    private IWorkingPrdCompanyRateDao workingPrdCompanyRateDao;

    // V3信贷产品期数公司利率
    @Resource
    private ICarCreditDetailDao carCreditDetailDao;

    @Resource
    private IRansomFloorDetailDao ransomFloorDetailDao;

    // 产品关联信息Dao
    @Resource
    private ICrmProductSignDao crmProductSignDao;
    @Resource
    private ICrmProductProductDao crmProductProductDao;
    @Resource
    private ICrmProductPaperTypeDao crmProductPaperTypeDao;

    // 信贷产品V1，V2,V3整合  数据库视图prd_view_crmV2对应类
    @Resource
    private IVCrmProductDao vCrmProductDao;*/

    /**
     * 功能说明：得到产品系列列表
     * huangyuanlong  2016-6-27
     * @return
     */
    public JSONObject getProductSeries() throws Exception{
        //返回的json 数据
        JSONObject json = new JSONObject();
        json.put(MsgConsts.RESPONSE_CODE, MsgConsts.SUCCESS_CODE);
        json.put(MsgConsts.RESPONSE_INFO, "操作成功!");
        //获得公司       类型  公司  状态 启用
        //String sql="select * from pro_crm_product p where p.parent_id='0'";
        List list= crmProductMapper.getProductSeries();
        //queryBySqlReturnMapList(sql);
        int total = crmProductMapper.countCrmProduct();
        json.put("data", list);
        json.put("total", total);

        return json;
    }

    @Override
    public JSONObject getProductType(String id) throws Exception{
        //返回的json 数据
        JSONObject json = new JSONObject();
        List list= crmProductMapper.getProductType(id);
        json.put("data", list);
        return json;
    }

    @Override
    public JSONObject getTypeDetail(String id) throws Exception{
        //返回的json 数据
        JSONObject json = new JSONObject();
        List list= crmProductMapper.getTypeDetail(id);
        json.put("data", list);
        return json;
    }

    @Override
    public JSONObject getProductDetail(String crmProductId) throws Exception{
        //返回的json 数据
        JSONObject json = new JSONObject();
        List<Map> list= crmProductMapper.getProductDetail(crmProductId);
        json.put("data", list);
        return json;
    }

    @Override
    public int updateStatus(String id,int status) throws Exception{
        //返回的json 数据
        //JSONObject json = new JSONObject();
        int num = crmProductMapper.updateStatus(id,status);
        return num;
    }

    @Override
    public int countAbleType(String id) throws Exception{
        //返回的json 数据
        //JSONObject json = new JSONObject();
        int num = crmProductMapper.countAbleType(id);
        return num;
    }

    @Override
    public int countPublisProduct(String id) throws Exception{
        //返回的json 数据
        //JSONObject json = new JSONObject();
        int num = crmProductMapper.countPublisProduct(id);
        return num;
    }

    @Override
    public int updateProductStatus(String id,int status) throws Exception{
        int num = crmProductMapper.updateProductStatus(id,status);
        return num;
    }

    @Override
    public int addProductSeries(ProCrmProduct proCrmProduct) throws Exception{
        int num = crmProductMapper.addProductSeries(proCrmProduct);
        return num;
    }

    @Override
    public int updateProductSeries(ProCrmProduct proCrmProduct) throws Exception {
        int num = crmProductMapper.updateProductSeriesById(proCrmProduct);
        return num;
    }

    @Override
    public JSONObject getProductInfo(String id) throws Exception {
        //返回的json 数据
        JSONObject json = new JSONObject();
        List<Map> list= crmProductMapper.getProductInfo(id);
        json.put("data", list);
        return json;
    }

    @Override
    public String getNumber( ) throws Exception {
        List list = crmProductMapper.getLastNumber();
        //判断查询集合是否为空
        if(0 != list.size()){
            //集合不为空将第一个转换成对象
            ProCrmProduct detail = (ProCrmProduct)list.get(0);

            //返回编号
            return detail.getPro_number();
        }else{
            //如果集合为空，直接返回0
            return "0";
        }
    }

    @Override
    public int addOrUpdateProductDetail(WorkingProductDetail workingProductDetail,String flag) throws Exception {
        int num =0;
        if("add".equals(flag)){
             num = crmProductMapper.insertProductDetail(workingProductDetail);
        }else if("update".equals(flag)){
            num = crmProductMapper.updateProductDetail(workingProductDetail);
        }
        return num;
    }

    @Override
    public  JSONObject selectProduct(String proName,String SeriesId) throws Exception {
        JSONObject json = new JSONObject();
        List<Map> list= crmProductMapper.selectProduct(proName,SeriesId);
        json.put("data", list);
        return json;
    }


    /**
     * 功能说明：查询费率
     * gaozhidong  2017-7-17
     * @return
     */
    @Override
    public List  getFeeList(Fee fee) {
        return crmProductMapper.getFeeList(fee);
    };

    public int delFee(String id){
        return crmProductMapper.delFee(id);
    }

    @Override
    public Fee getMagFeeList(Map map) {
        return feeMapper.getFeeByProductName(map);
    };

    /**
     * 功能说明：修改费率
     * gaozhidong  2017-7-17
     * @return
     */
    @Override
    public int updateFee(Fee fee) {
        return crmProductMapper.updateFee(fee);
    }

    public List<Fee> isExist(Fee fee){
        return crmProductMapper.isExist(fee);
    }
    /**
     * 功能说明：添加费率
     * gaozhidong  2017-7-17
     * @return
     */
    @Override
    public int addFee(Fee fee) {
        return crmProductMapper.addFee(fee);
    }

    /**
     * 功能说明：获取产品列表
     * gaozhidong  2017-7-17
     * @return
     */
    @Override
    public JSONObject getProduct(){
        //返回的json 数据
        JSONObject json = new JSONObject();
        List<Map> list= crmProductMapper.getProduct();
        json.put("data", list);
        return json;
    }

    @Override
    public JSONObject getProductDetailSelect(){
        //返回的json 数据
        JSONObject json = new JSONObject();
        List<Map> list= crmProductMapper.getProductDetailSelect();
        json.put("data", list);
        return json;
    }

    /**
     * 功能说明：获取产品分期列表
     * gaozhidong  2017-7-17
     * @return
     */
    @Override
    public JSONObject getPeriodsList(String crmProductId){
        //返回的json 数据
        JSONObject json = new JSONObject();
        List<Map> list= crmProductMapper.getPeriodsList(crmProductId);
        json.put("data", list);
        return json;
    }

    /**
     * @author:韩梅生
     * @Description 获取总包商列表
     * @Date 15:50 2018/5/18
     * @param
     */
    @Override
    public JSONObject getZbsList(){
        //返回的json 数据
        JSONObject json = new JSONObject();
        List<Map> list= crmProductMapper.getZbsList();
        json.put("data", list);
        return json;
    }

}
