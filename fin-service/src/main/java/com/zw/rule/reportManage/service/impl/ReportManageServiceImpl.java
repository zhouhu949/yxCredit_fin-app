package com.zw.rule.reportManage.service.impl;

import com.zw.base.util.DateUtils;
import com.zw.rule.mapper.reportMapper.ReportManageMapper;
import com.zw.rule.officeClerkEntity.Branch;
import com.zw.rule.po.SysDepartment;
import com.zw.rule.reportManage.Sorderdetail;
import com.zw.rule.reportManage.service.ReportManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/3/5.
 * 报表管理服务层实现方法
 */
@Service
public class ReportManageServiceImpl implements ReportManageService{
    @Resource
    private ReportManageMapper mapper;
    /**
     * 获取公司id
     * @param userId
     * @return
     */
    public String getCompanyIdByUserId(String userId) {
        return mapper.selectCompanyIdByUserId(userId);
    }
    //根据当前的部门id获取机构实体
    public Branch getBranchById(String id){
        return mapper.selectBranchById(id);
    };

    /**
     * 获取顶级父节点id
     * @return
     */
    public String getMostParsentId(){
        return mapper.selectMostParsentId();
    }

    /**
     * 获取二级节点id集合
     * @param topId
     * @return
     */
   public  List<String> getSecondIds(String topId){
       return mapper.selectSecondIds(topId);
   }

    /**
     * 获取当前登录人公司订单的个数
     * @param map
     * @return
     */
    public List<Map> getNowCompanyOrderCount(Map map){
        List list=new LinkedList();
        List<String> ids=mapper.selectNowCompanyOrderCount(map);
        String count=ids.size()+"";//当前登录人公司订单个数
        if("秒付金服".equals(map.get("nowCompanyName"))){//当前登录人的公司是秒付金服（能够看到自己的和下级子公司的）
            //自己公司的调用情况
            Map m=new HashMap();
            m.put("nowCompanyName",map.get("nowCompanyName").toString());
            m.put("count",count);
            list.add(m);
            List<String> nameList=mapper.selectSonCompanyNames(map.get("nowCompanyId").toString());//获取当前公司的全部子公司名称
            for(int i=0;i<nameList.size();i++){
                Map m0=new HashMap();
                Map m1=new HashMap();
                m1.put("nowCompanyName",nameList.get(i));
                m0.put("nowCompanyName",nameList.get(i));
                m0.put("count",mapper.selectNowCompanyOrderCount(m1).size());
                list.add(m0);
            }
        }else{//当前登录人的公司不是秒付金服，是秒付金服的子公司，他自能看到自己的调用情况
            Map m=new HashMap();
            m.put("nowCompanyName",map.get("nowCompanyName").toString());
            m.put("count",count);
            list.add(m);
        }
        return list;
    }

    /**
     * 代扣统计service层实现
     * @param map
     * @return
     */
    public List<Map> getNumOfDeducting(Map map){
        String nowCompanyName= map.get("nowCompanyName").toString();//当前用户公司名
        String nowCompanyId= map.get("nowCompanyId").toString();//当前用户公司id
        List<Map> list = mapper.getNumOfDeducting(map);//查询到的list
        List retureList = new ArrayList();//返回的list
        String topId = getMostParsentId();
        if( topId.equals(nowCompanyId) ){ //当前登录人公司id是秒付金服总公司不做处理
            return list;
        }else{
           for(int i=0;i<list.size();i++){
               String branchId=list.get(i).get("branch").toString();
               if(nowCompanyId.equals(branchId)){
                   retureList.add(list.get(i));
               }
           }
            return  retureList;
        }

    }




    /**
     * 销售订单报表导出excel专用service实现
     * @param map
     * @return
     */
    public List<Map> getReportAllToExcel(Map map){
        List<Map> listM=mapper.selectReportAllToExcel(map);
        for(int i=0;i< listM.size();i++){
            Map m=listM.get(i);
            //离还款日天数
            String dayForDueDate=getDayForPayTime(m);
            m.put("dayForDueDate",dayForDueDate);
            //格式化月息
            if(m.get("monthRate") != null &&  !"".equals(m.get("monthRate"))){
                m.put("monthRate",m.get("monthRate").toString()+"%");
            }
            //格式确认订单时间
            Object sureOrderTime=m.get("sureOrderTime");
            if(sureOrderTime ==null || "".equals(sureOrderTime.toString())){
                m.put("sureOrderTime","");
            }else{
                m.put("sureOrderTime",sureOrderTime);
            }
            //格式化发货时间
            Object fahuoTime=m.get("fahuoTime");
            if(fahuoTime ==null || "".equals(fahuoTime.toString())){
                m.put("fahuoTime","");
            }else{
                m.put("fahuoTime",formatTime(fahuoTime.toString()));
            }
            //格式化订单状态
            Object orderState=m.get("orderState");
            if(orderState !=null){
                if("0".equals(orderState.toString())){
                    m.put( "orderState","未提交");
                }else if("1".equals(orderState.toString())){
                    m.put( "orderState","借款申请");
                }else if("2".equals(orderState.toString())){
                    m.put( "orderState","自动化审批通过");
                }else if("3".equals(orderState.toString())){
                    m.put( "orderState","自动化审批拒绝");
                }else if("4".equals(orderState.toString())){
                    m.put( "orderState","自动化审批异常");
                }else if("5".equals(orderState.toString())){
                    m.put( "orderState", "人工审批通过");
                }else if("6".equals(orderState.toString())){
                    m.put( "orderState", "人工审批拒绝");
                }else if("7".equals(orderState.toString())){
                    m.put( "orderState", "合同确认");
                }else if("8".equals(orderState.toString())){
                    m.put( "orderState", "放款成功");
                }else if("9".equals(orderState.toString())){
                    m.put( "orderState", "结清");
                }else if("10".equals(orderState.toString())){
                    m.put( "orderState", "关闭");
                }else if("-1".equals(orderState.toString())){
                    m.put( "orderState", "待确认合同");
                }else if("-2".equals(orderState.toString())){
                    m.put( "orderState", "合同确认");
                }else if("-3".equals(orderState.toString())){
                    m.put( "orderState", "待付预付款");
                }else if("-4".equals(orderState.toString())){
                    m.put( "orderState", "待发货");
                }else if("-5".equals(orderState.toString())){
                    m.put( "orderState", "已发货");
                }else if("-6".equals(orderState.toString())){
                    m.put( "orderState", "还款中");
                }
            }
            //格式化预付款状态yufukuanState
            Object yufukuanState=m.get("yufukuanState");
            if(yufukuanState !=null && !"".equals(yufukuanState)){
                if(Double.parseDouble(yufukuanState.toString()) >= 18){
                    m.put("yufukuanState" ,"已支付");
                }else{
                    m.put("yufukuanState" ,"未支付");
                }
            }
            //格式化发货状态
            Object deliveryState=m.get("deliveryState");
            if(deliveryState !=null && !"".equals(deliveryState.toString())){
                if(Double.parseDouble(deliveryState.toString()) >= 19){
                    m.put("deliveryState","已发货");
                }else{
                    m.put("deliveryState","未发货");
                }
            }
            //格式化放款状态
            Object loanState=m.get("loanState");
            if(loanState !=null && !"".equals(loanState)){
                if( loanState.toString().equals("0")){
                    m.put("loanState","- -") ;
                }else if(loanState.toString().equals("1")){
                    m.put("loanState","待放款");
                }else if(loanState.toString().equals("2")){
                    m.put("loanState","放款中");
                }else if(loanState.toString().equals("3")){
                    m.put("loanState","已放款");
                }else if(loanState.toString().equals("4")){
                    m.put("loanState","放款失败");
                }
            }
            //格式化结算状态
            Object jsState=m.get("jsState");
            if(jsState !=null && !"".equals(jsState)){
                if(jsState.toString().equals("9")){
                    m.put("jsState","结清");
                }else{
                    m.put("jsState","未结清");
                }
            }
        }
        return listM ;
    }

    /**
     * 格式化时间
     * @param time
     * @return
     */
    public String formatTime(String time){
        if (null != time && !"".equals(time)) {
            time=time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8)+"  "+time.substring(8,10)+":"+time.substring(10,12);
        }
        return time;
    }

    /**
     * 获取离还款日天数 20180308040009  20180408 20180308
     * @param map
     * @return
     */
    public String getDayForPayTime(Map map){//参数：当前系统时间time，当前订单orderId
        Map m=new HashMap();
        m.put("orderId",map.get("orderId"));
        m.put("time", DateUtils.getCurrentTime().substring(0,8));
        //获取到的时间
        String nextPayDate=mapper.selectDayForPayTime(m);//20180308
        //当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        long days=0;
        if(nextPayDate !=null){
            try {
                Date nextPayDateD=sdf.parse(nextPayDate);
                Date now=sdf.parse(DateUtils.getCurrentTime().substring(0,8));
                days=(nextPayDateD.getTime() - now.getTime())/(24*60*60*1000);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return days+"";
    }

    /**
     * 逾期报表service实现
     * @param map
     * @return
     */
    public List<Map> getYuQiReportToExcel(Map map){
        map.put("dqsj",DateUtils.getCurrentTime().substring(0,8));//当前时间
        List<Map> yuQiList=mapper.selectYuQiReportToExcel(map);
        for(Map yuQiMap:yuQiList){
            List<Map> listM=mapper.getLinkManByCustomerId(yuQiMap.get("customerId").toString());
            for(int i = 0;i<listM.size();i++){
                yuQiMap.put("link"+i,listM.get(i).get("link_name")+":"+listM.get(i).get("contact")+"("+listM.get(i).get("relationship_name")+")");
            }
        }
        return  yuQiList;
    }

}
