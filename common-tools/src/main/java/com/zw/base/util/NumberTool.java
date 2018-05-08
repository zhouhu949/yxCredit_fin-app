package com.zw.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 
 * 功能说明:数值的工具类
 * 典型用法：
 * 特殊用法：
 * @author wangmin
 * 修改人: 
 * 修改原因：
 * 修改时间：
 * 修改内容：
 * 创建日期：2017-7-6
 * Copyright zzl-apt
 */
public class NumberTool {

	/**
	 * 
	 * 功能说明：格式化double 
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：wangmin
	 * 修改内容：
	 * 修改注意点：
	 */
	public static String format(double val,String pattern ){
		DecimalFormat  df=new DecimalFormat(pattern);
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(val);
	}
	/**
	 * 
	 * 功能说明：格式化double 精确到2位
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：wangmin
	 * 修改内容：
	 * 修改注意点：
	 */
	public static String format(double val ){
		/*DecimalFormat  df=new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(val);*/
		//改进型
		String aNew= String.valueOf(val);
		BigDecimal bd =new  BigDecimal(aNew);
		bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
		return bd.toString();
	}
	/**
	 * 
	 * 功能说明：格式化金额  元to分
	 * yuanhao  2017-7-6
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：yuanhao
	 * 修改内容：
	 * 修改注意点：
	 */
	public static String duelmoney(double money) {
		double baseAmt= ArithUtil.mul(money, 100);
		DecimalFormat  df=new DecimalFormat("0");
		df.setRoundingMode(RoundingMode.HALF_UP);
		String amt=df.format(baseAmt);
		return amt;
	}
	/**
	 * 
	 * 功能说明：object  to  double
	 * yuanhao  2017-7-6
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：wangmin
	 * 修改内容：
	 * 修改注意点：
	 */
	public static double parseDouble(Object obj){
		if(obj == null ||"".equals(obj) ){
			obj="0";
		}
		String o = obj.toString();
		return Double.parseDouble(o);
	}
	/**
	 * 功能说明：格式化double
	 * yuanhao  2017-7-6
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：wangmin
	 * 修改内容：
	 * 修改注意点：
	 */
	public static String formatDouble(double num){
		/*DecimalFormat  df=new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(num);*/
		//改进型
		String aNew= String.valueOf(num);
		BigDecimal bd =new  BigDecimal(aNew);
		bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
		return bd.toString();
	}
	/**
	 * 功能说明：传递一个参数值,顺序扣减金额,获得减去的条数和次数
	 * yuanhao  2017-7-6
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：wangmin
	 * 修改内容：
	 * 修改注意点：
	 */
	public static Map<String,Object> calcEveryMoneyToReduce(String[] orders, JSONObject paramsJson, List<JSONObject> baseList, String[] moneyOrder){
		//
		double money =0d ;
		double totalMoney = 0d;
		for(String mo:moneyOrder){
			if(paramsJson.containsKey(mo)){
				money = ArithUtil.add(money,paramsJson.getDouble(mo));
				totalMoney = ArithUtil.add(money,paramsJson.getDouble(mo));
			}
		}
		//返回的结果
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<JSONObject> resultList = new ArrayList<JSONObject>(); //重新进行baselist的排序
		//排序
		sortList(baseList);
		for(int i=0;i<baseList.size();i++){ //开始进行循环扣款
			String firstKey = orders[0];//从第一个开始扣除
			//每一条扣的数据都需要进行记录存储
			JSONObject recordJson = new JSONObject();//记录的json
			JSONObject baseJson = baseList.get(i);
			int orderColumn = baseJson.getInteger("orderColumn");
			double currentMoney = baseJson.getDouble(firstKey);//第一个要扣的钱
			if(money>=currentMoney){ //若要扣除的金额大于基础金额则基础金额是可以直接扣除完成的开始寻找下一个基础金额扣款
				recordJson.put(firstKey, currentMoney);
				money = ArithUtil.sub(money, currentMoney);
			}else { //如果不够减的则剩余的金额全部扣除即可
				recordJson.put(firstKey, money);
				money = 0;
			}
			if(money <= 0){ //没钱了,这直接停止,将未赋值的数据直接赋值0,跳出循环
				int j = 0;
				String nextKey=firstKey;
				for(;;){
					nextKey  = next(nextKey, orders);
					if("".equals(nextKey)){ //到最后了
						break;
					}
					recordJson.put(nextKey, 0d);
					if(j>(orders.length+1)){ //20次还是没有跳出,则说明一定出现了问题
						break;
					}
					j++;
				}
				recordJson.put("orderColumn",orderColumn);
				resultList.add(recordJson);
				break;
			}
			//说明money肯定还是有钱则继续下一个基础金额的扣
			for(;;){
				firstKey  = next(firstKey, orders);
				if("".equals(firstKey)){ //到最后了
					break;
				}
				currentMoney = baseJson.getDouble(firstKey);//下一个要扣的钱
				if(money>=currentMoney){ //若要扣除的金额大于基础金额则基础金额是可以直接扣除完成的开始寻找下一个基础金额扣款
					recordJson.put(firstKey, currentMoney);
					money = ArithUtil.sub(money, currentMoney);
				}else { //如果不够减的则剩余的金额全部扣除即可
					recordJson.put(firstKey, money);
					money = 0;
				}
				if(money <= 0){ //没钱了,这直接停止,将未赋值的数据直接赋值0,跳出循环
					int j = 0;
					for(;;){
						
						firstKey  = next(firstKey, orders);
						if("".equals(firstKey)){ //到最后了
							break;
						}
						recordJson.put(firstKey, 0d);
						if(j>(orders.length+1)){ //20次还是没有跳出,则说明一定出现了问题
							break;
						}
						j++;
					}
					break;
				}
				
				
			}
			//
			recordJson.put("orderColumn",orderColumn);
			resultList.add(recordJson);
			if(money<=0){
				break;
			}
			
		}
		resultMap.put("resultData", resultList);
		// 开始进行金额的分配  --->> 按照数组的顺序进行本金和利息的重新分配
		for(int i=0;i<moneyOrder.length;i++){
			List<JSONObject> moneyList = new ArrayList<JSONObject>();
			String moneyKey = moneyOrder[i];
			double thisMoney = 0d;
			if(!paramsJson.containsKey(moneyKey) ||  paramsJson.getDouble(moneyKey)==0d){ //不符合规则或者是金额为0
				resultMap.put(moneyKey, moneyList);//赋值为空
				continue;
			}
			thisMoney = paramsJson.getDouble(moneyKey);
			for(JSONObject baseJson:resultList){
				JSONObject moneyJson = new JSONObject();//用来记录占比的钱
				for(String orderKey:orders){ //用来分配其中的没一个金额
					moneyJson.put("orderColumn", baseJson.getString("orderColumn"));
					double orderMoney = baseJson.getDouble(orderKey);  //这个比例是最大的
					double rate = ArithUtil.div(thisMoney, totalMoney);
					moneyJson.put(orderKey, ArithUtil.mul(orderMoney, rate));
				}
				moneyList.add(moneyJson);
				
			}
			resultMap.put(moneyKey, moneyList);
		}
		//开始检测数据的完整性
		resultMap = fixIntegrity(resultMap, resultList, moneyOrder, orders);
			
		
		
		return resultMap;
	}
	/**
	 * 功能说明：检测数据的完整性
	 * yuanhao  2017-7-6
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：wangmin
	 * 修改内容：
	 * 修改注意点：
	 */
	public static Map<String,Object>  fixIntegrity(Map<String,Object> baseMap,List<JSONObject> baseList,String[] moneyOrders,String[] orders){
		//循环list查询 对应的总金额
		for(JSONObject baseJson:baseList){
			for(String orderKey:orders){
				//改键下的总金额
				double totalKeyMoney = baseJson.getDouble(orderKey);
				int orderColumn = baseJson.getInteger("orderColumn"); //找到对应的期数列
				if(totalKeyMoney<=0){
					continue;
				}
				//查找对应下的所有的Json数据
				double checkMoney = 0;
				JSONObject lastJson = new JSONObject();
				for(int i=0;i<moneyOrders.length;i++){ //找到对应下的金额
					List<JSONObject> moneyJsonList = (List<JSONObject>) baseMap.get(moneyOrders[i]);
					if(ListTool.isNullOrEmpty(moneyJsonList)){ //说明并没有参与到资金的计算
						continue;
					}
					//找到对应这一期
					JSONObject json =ListTool.getJsonByKey(moneyJsonList,"orderColumn", String.valueOf(orderColumn));
					checkMoney = ArithUtil.add(checkMoney,json.getDouble(orderKey));
					if(i==(moneyOrders.length-1)){
						lastJson = json;
					}
				}
				//开始进行金额的比较
				if(totalKeyMoney>checkMoney){  //有剩余的本金并没有进行存储 >> 直接赋值给最后一期
					lastJson.put(orderKey, ArithUtil.add(lastJson.getDouble(orderKey),ArithUtil.sub(totalKeyMoney, checkMoney)));
				}
				if(totalKeyMoney<checkMoney){  //不仅没有剩余反而金额少了,则直接从最后一期扣除
					lastJson.put(orderKey, ArithUtil.add(lastJson.getDouble(orderKey),ArithUtil.sub(totalKeyMoney, checkMoney)));
				}
				
				
			}
			
			
		}
		return baseMap;
	}
	/**
	 * 功能说明：寻找key键的下一个值
	 * wangmin  2017-7-6
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：wangmin
	 * 修改内容：
	 * 修改注意点：
	 */
	public static String  next(String key,String[] orders){
		String resultKey ="";
		for(int i=0;i<orders.length;i++){
			String thisKey = orders[i];
			if(i==(orders.length-1)){ //检测到了最后一个则他其实就是最后一个了
				break;
			}
			if(thisKey.equals(key)){ //则下一个就是需要找的值
				resultKey= orders[i+1];
				break;
			}
		}
		return resultKey;
	}
	/**
	 * 功能说明：寻找key键的下一个值
	 * wangmin  2017-7-6
	 * @param 
	 * @return   
	 * @throws  该方法可能抛出的异常，异常的类型、含义。
	 * 最后修改时间：
	 * 修改人：wangmin
	 * 修改内容：
	 * 修改注意点：
	 */
	public static void  sortList(List<JSONObject> baseList){
		Collections.sort(baseList,new Comparator<JSONObject>(){
			public int compare(JSONObject o1, JSONObject o2) {  
            if(o1.getDouble("orderColumn") > o2.getDouble("orderColumn")){  
                return 1;  
            }  
            if(o1.getDouble("orderColumn") == o2.getDouble("orderColumn")){  
                return 0;  
            }  
            return -1;  
        } 
	});
	}
	public static void main(String[] args) {
		//开始检测算法
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("liveMoney", 100d);
		paramsJson.put("cardMoney",0d);
		paramsJson.put("baseMoney", 0d);
		String[] orders = {"prepayment_violate_money","interest","overdue_violate_money","accrual","capital","manage_fee"};
		String[] money={"liveMoney","cardMoney","baseMoney"};
		List<JSONObject> baseList = new ArrayList<JSONObject>();
		for(int i=3;i>0;i--){
			JSONObject baseJson = new JSONObject();
			baseJson.put("prepayment_violate_money", 0d);
			baseJson.put("interest", 0d);
			baseJson.put("overdue_violate_money", 0d);
			baseJson.put("accrual", 0d);
			baseJson.put("capital", 100d);
			baseJson.put("manage_fee", 0d);
			baseJson.put("orderColumn", i);
			baseList.add(baseJson);
		}
		System.out.println(JSONObject.parseObject(JSON.toJSONString(calcEveryMoneyToReduce(orders, paramsJson, baseList, money))));
	}
}
