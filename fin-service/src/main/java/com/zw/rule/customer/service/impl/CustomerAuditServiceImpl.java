package com.zw.rule.customer.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zw.UploadFile;
import com.zw.base.util.DateUtils;
import com.zw.rule.answer.po.Answer;
import com.zw.rule.answer.po.AnswerReturn;
import com.zw.rule.core.Response;
import com.zw.rule.customer.po.*;
import com.zw.rule.customer.service.CustomerAuditService;
import com.zw.rule.customer.service.CustomerService;
import com.zw.rule.mapper.OfficeClerkManager.OfficeClerkManageMapper;
import com.zw.rule.mapper.answerMapper.AnswerMapper;
import com.zw.rule.mapper.customer.*;
import com.zw.rule.officeClerk.service.OfficeClerkManageService;
import com.zw.rule.officeClerkEntity.Branch;
import com.zw.rule.officeClerkEntity.OfficeClerkManager;
import com.zw.rule.po.Dict;
import com.zw.rule.service.DictService;
import com.zw.rule.util.HttpUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *
 *Filename：
 *          客户审核业务逻辑
 *Description：
 *		  未知
 *Author:
 *		 李开艳,王涛
 *Finished：
 *		2017/6/22
 ********************************************************/
@Service
public class CustomerAuditServiceImpl implements CustomerAuditService{

    @Resource
    private CustomerInvestigationMapper customerInvestigationMapper;
    @Resource
    private CustomerRespMapper customerRespMapper;
    @Resource
    private CustomerExamineMapper customerExamineMapper;
    @Resource
    private CustomerMatchingMapper customerMatchingMapper;
    @Resource
    private CustomerImageMapper customerImageMapper;
    @Resource
    private CustomerService customerService;
    @Resource
    private DictService dictService;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private AnswerMapper answerMapper;
    @Resource
    private OfficeClerkManageMapper officeClerkManageMapper;
    @Resource
    private OfficeClerkManageService officeClerkManageService;
    @Override
    public Map addCustomerInvestigation(CustomerInvestigation customerInvestigation) {
        String uuid = UUID.randomUUID().toString();
        customerInvestigation.setId(uuid);
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMddHHmmss" );
        customerInvestigation.setAnswerTime(sdf.format(new Date()));
        int num = customerInvestigationMapper.insert(customerInvestigation);
        Map map = new HashMap();
        map.put("flag",num>0);
        map.put("id",uuid);
        return map;
    }

    @Override
    public Boolean deleteCustomerInvestigation(String id) {
        int num =  customerInvestigationMapper.deleteByPrimaryKey(id);
        return num>0;
    }

    @Override
    public Boolean addCustomerExamine(CustomerExamine customerExamine) {
        int num = customerExamineMapper.insert(customerExamine);
        return num>0;
    }

    @Override
    public List getMatchingPrice(CustomerMatching customerMatching) {
        List list = customerMatchingMapper.getMatchingPrice(customerMatching);
        return list;
    }

    @Override
    public List uploadCustomerImage(HttpServletRequest request) throws Exception {
        String fileName="";
        String id = UUID.randomUUID().toString();
        //获取根目录
        String root = request.getSession().getServletContext().getRealPath("/");
        //捕获前台传来的图片，并用uuid命名，防止重复
        Map<String, Object> allMap = UploadFile.getFile(request,root+ File.separator + "firstAudit", id);
        List<Map<String, Object>> list = (List<Map<String, Object>>) allMap.get("fileList");
        //当前台有文件时，给图片名称变量赋值
        if (!list.isEmpty()) {
            Map<String, Object> fileMap = list.get(0);
            fileName = "/firstAudit/"+(String) fileMap.get("Name");
        }
        List imageList = new ArrayList();
        imageList.add(fileName);
        return imageList;
    }

    @Override
    public CustomerResp selectById(String id){
        CustomerResp customerResp = customerRespMapper.selectByPrimaryKey(id);
        if(customerResp!=null){
            return customerResp;
        }
        return null;
    }

    @Override
    public List<CustomerImage> getOrderImage(String orderId) {
        return customerImageMapper.getOrderImage(orderId);
    }
    //获取信用问答题目
    @Override
    public Response getAnswer(Map map){
        Response response=new Response();
        List<Answer> answerList=new ArrayList<Answer>();
        String orderId=map.get("orderId").toString();
        Order order=orderMapper.selectByPrimaryKey(orderId);
        OfficeClerkManager officeClerkManager=officeClerkManageMapper.selectOneManagerById(order.getEmpId());
        //已提交答案
        if ("2".equals(order.getAnswerState())){
            answerList=answerMapper.getAnswerList(map);
            response.setData(answerList);
        }
        //已获取题目
        else if ("1".equals(order.getAnswerState())){
            answerList=answerMapper.getAnswerList(map);
            response.setData(answerList);
        }else {
            MagCustomerLive magCustomerLive = customerService.getCustomerLive(map.get("customerId").toString());
            Customer customer= customerService.getCustomer(map.get("customerId").toString());
            Dict dict=dictService.getDetailListByCode("answerCount").get(0);
            Map<String , Object> param = new HashMap<String , Object>();
            param.put("priority", 1);
            param.put("pid", UUID.randomUUID()+"");
            param.put("subPid", UUID.randomUUID()+"");
            param.put("name", customer.getPersonName());
            param.put("idNo", customer.getCard());
            param.put("phone", customer.getTel());
            param.put("itemType", "address");//address 地址,companyName 公司名,job 职业
            param.put("itemValue", magCustomerLive.getProvinces()+magCustomerLive.getCity()+magCustomerLive.getDistric());
            param.put("city", magCustomerLive.getCity());
//            param.put("itemValue", "天鹅湖畔小区");
//            param.put("city", "合肥");
            param.put("questionCount",dict.getCode());
            param.put("companyName",officeClerkManager.getCompany());
            //此时需要放入公司id，但是我们获取到的可能是部门id 也可能是公司id
            Branch b=officeClerkManageService.getBranch(officeClerkManager.getBranchId());//部门或者公司实体
            Branch branch=officeClerkManageService.getGongSiName(b);//获取到的公司实体
            param.put("companyId",branch.getId());//放入公司id
//            param.put("companyId",officeClerkManager.getBranchId());
            param.put("busType",2);
            String result;
            String url=map.get("url").toString();
            //url = "http://192.168.102.40:8888/szt/jxlQuestion/getQuestion";/
            try{
                result  = HttpUtil.doGet(url, param);
                response.setMsg(JSONObject.parseObject(result).getJSONObject("data").get("note").toString());
                JSONArray jsonArray= JSONObject.parseObject(result).getJSONObject("data").getJSONObject("data").getJSONArray("exam");
                String examId=JSONObject.parseObject(result).getJSONObject("data").getJSONObject("data").getString("examId");
                System.out.println("=================================================");
                System.out.println(result);
                System.out.println("=================================================");
                for (int i=0;i<jsonArray.size();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    Answer answer=new Answer();
                    answer.setId(UUID.randomUUID().toString());
                    answer.setState("1");
                    answer.setOrderId(orderId);
                    answer.setAnswerTimeSuggestion(jsonObject.getString("answerTimeSuggestion"));
                    answer.setQuestionId(jsonObject.getString("questionId"));
                    answer.setQuestionTitle(jsonObject.getString("questionTitle"));
                    answer.setChoiceCode(jsonObject.getString("A"));
                    answer.setChoiceList(jsonObject.getJSONArray("choiceList").toString());
                    answer.setCreatTime(DateUtils.getDateString(new Date()));
                    answer.setExamId(examId);
                    answerList.add(answer);
                }
            }catch (Exception e) {
                response.setCode(1);
                if (response.getMsg()==null){
                    response.setMsg("获取题目出现异常");
                }
                e.printStackTrace();
                return  response;
            }
            answerMapper.insertAnswerList(answerList);
            Map<String,Object> updateOrder=new HashedMap();
            updateOrder.put("id",map.get("orderId"));
            updateOrder.put("answerState","1");
            orderMapper.updateOrderStatus(updateOrder);
            response.setData(answerList);
        }
        return  response;
    }

    @Override
    public Response addAnswer(List<Map<String,Object>> listMap,String url){
        Response response=new Response();
        Map<String,Object> map=new HashedMap();
        Map<String,Object> param = new HashMap<String , Object>();
        ArrayList<AnswerReturn> list = new ArrayList<AnswerReturn>();
        List<Answer> answerList=new ArrayList<Answer>();
        //查询分公司名称
        //Map departmentMap=customerService.getDepartmentInfo();
        String score="";
        String examId="";
        String orderId="";
        for (int i=0;i<listMap.size();i++){
            AnswerReturn answerReturn=new AnswerReturn();
            map=listMap.get(i);
            examId=map.get("examId").toString();
            orderId=map.get("orderId").toString();
            answerReturn.setQuestionId(map.get("questionId").toString());
            answerReturn.setAnswer(map.get("choiceCode").toString());
            answerReturn.setSpendTime("10000");
            list.add(answerReturn);
        }
        Order order=orderMapper.selectByPrimaryKey(orderId);
        OfficeClerkManager officeClerkManager=officeClerkManageMapper.selectOneManagerById(order.getEmpId());
        //officeClerkManageMapper.selectOneManagerById();
        param.put("examId",examId);
        param.put("answerList", list);
        param.put("companyName",officeClerkManager.getCompany());
//        param.put("companyId",officeClerkManager.getBranchId());
        //此时需要放入公司id，但是我们获取到的可能是部门id 也可能是公司id
        Branch branch1=officeClerkManageService.getGongSiName(officeClerkManageService.getBranch(officeClerkManager.getBranchId()));//获取到的公司实体
        param.put("companyId",branch1.getId());//放入公司id
        param.put("busType",2);
        param.put("pid", UUID.randomUUID()+"");
        JSONObject json = (JSONObject) JSONObject.toJSON(param);
        String result;
        //url = "http://192.168.102.40:8888/szt/jxlQuestion/submitAnswer";
        try{
            result  = HttpUtil.doPost(url, json.toString());
            JSONArray jsonArray= JSONObject.parseObject(result).getJSONObject("data").getJSONObject("data").getJSONObject("exam").getJSONArray("question");
            score=JSONObject.parseObject(result).getJSONObject("data").getJSONObject("data").getString("score");
            for (int i=0;i<jsonArray.size();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                for (int j=0;j<listMap.size();j++){
                    map=listMap.get(j);
                    if (map.get("questionId").toString().equals(jsonObject.getString("questionId"))){
                        map.put("correctAnswer",jsonObject.getString("correctAnswer"));
                        answerMapper.updateAnswer(map);
                    }
                }
            }
            Map<String,Object> updateOrder=new HashedMap();
            updateOrder.put("id",orderId);
            updateOrder.put("answerState","2");
            updateOrder.put("answerScore",score);
            orderMapper.updateOrderStatus(updateOrder);
            System.out.println("=================================================");
            System.out.println(result);
            System.out.println("=================================================");
            answerList=answerMapper.getAnswerList(map);
            List dataList= new ArrayList();
            dataList.add(score);
            dataList.add(answerList);
            response.setData(dataList);
        }catch (Exception e) {
            for (int i=0;i<listMap.size();i++){
                map=listMap.get(i);
                answerMapper.updateAnswer(map);
            }
            response.setMsg("获取问卷分数出现异常，请稍后重试！");
            e.printStackTrace();
            return response;
        }
        response.setMsg("获取成功！");
        return response;
    }
}
