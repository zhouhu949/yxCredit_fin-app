package com.zw.rule.web.samples.sign;

import com.junziqian.api.bean.ResultInfo;
import com.junziqian.api.util.HttpSignUtils;
import com.junziqian.api.util.ResultInfoException;
import com.zw.rule.web.samples.JunziqianClientInit;
import org.ebaoquan.rop.thirdparty.com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理http回调
 * @author yfx
 * 2016年7月21日 下午4:19:03
 */
public class CallBackSample extends JunziqianClientInit {


	/**
	 * 以下例子为使用springMvc的一个接收打印例子（post请求）
	 * 更新回调地址
	 * @return
	 * @throws IOException
	 */
	/**
	@RequestMapping("/affirmBack")
	@ResponseBody
	public Object affirmBack(HttpServletRequest request) throws IOException {
		// 输出请求参数
		logger.info("======heads:");
		Enumeration<?> enums = request.getHeaderNames();
		while (enums.hasMoreElements()) {
			Object obj = enums.nextElement();
			logger.info(obj+"|"+request.getHeader(obj + ""));
		}
		logger.info("======params:");
		enums = request.getParameterNames();
		while (enums.hasMoreElements()) {
			Object obj = enums.nextElement();
			logger.info("======key:" + obj + ",value:" + request.getParameter(obj + ""));
		}
		logger.info("======inputStream:");
		List<String> list = IOUtils.readLines(request.getInputStream());
		for (String str : list) {
			logger.info(str);
		}
		return ResultInfo.create().success();
		//return JSON.toJSONString(ResultInfo.create().success());
	}**/

	/**
	 * 模拟签约回调的请求情况
	 */
	public static void signInfoCallBack(){
		long timestamp= 1494235767093L;//request请求传过来的时间戳timestamp参数
		String sign="8b069524b034cc7797ff016457xxxxxxxxxxxxxxxxxx";//request请求传过来的sign参数

		//这里就不模拟request请求了，已将请求的参数写入一个map中下面直接验证（其它参数放入bodyPrams中）
		Map<String,Object> bodyParams=new HashMap<String,Object>();
		bodyParams.put("applyNo", "APL86151252xxxxxxxxxxx");//这里指约定传过来的参数
		bodyParams.put("identityType", "1");//这里指约定传过来的参数
		bodyParams.put("fullName", "张xxx");//这里指约定传过来的参数
		bodyParams.put("identityCard", "41042119xxxxxxxxxxxx");//这里指约定传过来的参数
		bodyParams.put("optTime", "14942xxxxxxxxxxxx");//这里指约定传过来的参数
		bodyParams.put("signStatus", "1");//这里指约定传过来的参数

		//上面的参数请参考affirmBack方法获取
		try{
			//HttpSignUtils.checkHttpSign(bodyParams, timestamp, JunziqianClientInit.APP_KEY, JunziqianClientInit.APP_SECRET, sign);
			HttpSignUtils.checkHttpSign(bodyParams, timestamp, "你的appkey", "你的secret", sign,1000*60*60*24);
			//TODO处理其它业务
			//最后请返回以下内容的字符串
			ResultInfo ri=ResultInfo.create().success();
			System.out.println(JSON.toJSONString(ri));
			//如有异常请返回
			//ResultInfo.create().fail("处理失败");
		}catch(ResultInfoException e){
			System.out.println(e.getResultCode()+"===>"+e.getMessage());
		}
	}

	/**
	 * 模拟其它情况的回调处理
	 */
	public static void otherCallBack(){
		long timestamp= 1494235767093L;//request请求传过来的时间戳timestamp参数
		String sign="8b069524b034cc7797ff016457xxxxxxxxxxxxxxxxxx";//request请求传过来的sign参数

		//这里就不模拟request请求了，已将请求的参数写入一个map中下面直接验证（其它参数放入bodyPrams中）
		Map<String,Object> bodyParams=new HashMap<String,Object>();
		bodyParams.put("method", "bank.three.status");//处理类型
		bodyParams.put("version", "1.0.0");
		bodyParams.put("data", "{里面是一个json字符串}");//业务处理的结果
		bodyParams.put("timestamp", timestamp);
		bodyParams.put("sign", sign);

		//上面的参数请参考affirmBack方法获取
		try{
			//HttpSignUtils.checkHttpSign(bodyParams, timestamp, JunziqianClientInit.APP_KEY, JunziqianClientInit.APP_SECRET, sign);
			HttpSignUtils.checkHttpSign(bodyParams, timestamp, "你的appkey", "你的secret", sign,1000*60*60*24);
			//TODO处理其它业务
			//最后请返回以下内容的字符串
			ResultInfo ri=ResultInfo.create().success();
			System.out.println(JSON.toJSONString(ri));
			//如有异常请返回
			//ResultInfo.create().fail("处理失败");
		}catch(ResultInfoException e){
			System.out.println(e.getResultCode()+"===>"+e.getMessage());
		}
	}

	public static void main(String[] args) {
		//签约回调（签约完成和保全成功的回调）
		//signInfoCallBack();
		//其它回调，如3要素验证的回调（3要素使用api的情况）
		//otherCallBack();
	}
}
