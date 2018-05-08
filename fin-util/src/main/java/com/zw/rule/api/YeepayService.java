package com.zw.rule.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yeepay.g3.sdk.yop.client.YopClient3;
import com.yeepay.g3.sdk.yop.client.YopRequest;
import com.yeepay.g3.sdk.yop.client.YopResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;


public class YeepayService {
	
	static String appKey= "SQKK10015908980";		//	Config.getInstance().getValue("appKey");
	static String secretKey="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCqcnTejUSBAHaFpNiO9p5wsWErV7JcA+ocS8lcdUFiceCIQfumeUHFj1D99o92zuRPY0i6R7NNCg4tAbkh7p82MsGzDbQjRwRF1wvPm25lYbuvGvGSBRWo4Wiih5sRetkwUweJXRAmKdcyK1/S/hOmaUGqZ+P2cfuR7mBtShY8T6Cm2QujlkZoVt+H5XqUQilwvYWcQnvLCnAEjbTzG7xt4umiad4fB9K3h5K59UpYpuFC99ayJApHX9Ds5kqowm43+sJ2TZmD9wC/OFmmXe+Y+9eKQmdNq4KFQSSZW/Yf+R62YbmUMizZTduvODNH2eEMCqJ36mnFbwa4DKeq3krfAgMBAAECggEAM6duHYfoS8PtJ0E209SPXY2T6gOwrMwO5bZd9qQeRYxHRGPitKeotAtjuyM+hP3cGOb4wmM6Rk2W0DLmfQ8Itu8Y3n93qP07oUKzdJ4hDZ5Zt45NyTe7QavV/vNjnh+BtHBlJ07JtxcU/UJBzvpq0BKz8MV9Q1F1sNrx5A0AwhPMcAZ37j736o23+bZ8ipN7f0/7WJ/t/OgCO8/Cx1E3BtD7cdqD94Mv8Xpm+tC/EkSGQBeqngb1z2AZnFxVRd38mcrw5vW0Q2RTkd+JvhYO55ByouMJrsZsskNcfRlYFeJ+JMJNHCC+WsrWB2iODglD3bgc1a6DvPmiCDRh9QS6+QKBgQDlI9kvGlY5mAmZaWdhu2POmEtqBcv3Lm2hn4XmCWbWAdONcNvEQt9kNIDfrZfOqoT/kIf6mC5L4W1U1yFNvoc4mbcsRYRg3quLk0opVePL/atWgVjHG/Y6IR93tILFNqNkbYrZpW3Tc/g/RqhBsaJ+TEhelql2tDXJ4SU+Ab9k6wKBgQC+bVIFLkBEX7qcAn2Na3LXh3EA7+nBgRfjFbH4CuW7+e9+ZEn7XOWoY+6aVg72FFoZbHKVTYlex2tdBzEsyMeLkZzCwEpzCMTaL0fDbZJLVlZ4JkrLAL0+EhNiTxLYAlIAkXQagqNfFgxSty5jrYDypggRaC1nSU0m50tODxGE3QKBgEDdPQXQdsgm+dCrvdA0s7Qv+Gky6uI2CmLOPaE42BuMuM45PHz6UTKUikbHZUnji3Ks/1E48yIX1lNF8u+HF9A181xc8XRalEUWlM/OuIVucaozQ2ZZzAH4jmfceYhKR0aOm9ewtL4+/e8rmUW/ezg1b+cWzzIDIZbcXSaIaB2xAoGARv8XH1tZFqTiPBwplzpCPN0AYqsP6dcpgr6p9aKKeIT8p5DYjGDcNuXbJauENHbuCKCIL+YSm0WaX7q4uMu6qeyGF439s9nHGtmZ1eDaNEBiSLVuGTWTWLsAgxycF/D1hcS1FtUx99eOjKBDKWKcutrwEx1WIDYSD7kYOGghzfkCgYEAzId313Z31+fhVP83qAx+XiyBrTe84Nb7gnxpbEO46IaWVj+YJM2npRCZyVmFTe7cMciZMcD1LTXLsLYKjAVoaBuIoc5zl12FhLn0NlTqtiSPu2sNcOkjrOZgI8ko8yQjsAaKvc25TgeH5JIdlcLsIjBPlCISeq4CwhS/DzOqKUA="; 	// new test().getvalue("isv_private_key");
	static String serverRoot="https://open.yeepay.com/yop-center";//		Config.getInstance().getValue("serverRoot");

    
        public static Map<String,String> yeepayYOP(Map<String,String> map,String Uri){

        	System.out.println("********"+secretKey);
        	YopRequest yoprequest  =  new YopRequest(appKey,secretKey,serverRoot);
        Map<String,String> result  =  new HashMap<String,String>();
        
        Set<Entry<String,String> > entry		=	map.entrySet();
        	for(Entry<String,String> s:entry){
        		yoprequest.addParam(s.getKey(), s.getValue());
        	}
        	System.out.println("yoprequest:"+yoprequest.getParams());
    
       	//向YOP发请求
        	YopResponse yopresponse = YopClient3.postRsa(Uri, yoprequest);
        	System.out.println("请求YOP之后结果："+yopresponse.toString());
        	System.out.println("请求YOP之后结果："+yopresponse.getStringResult());
	
        	
        	
//        	对结果进行处理
        		if("FAILURE".equals(yopresponse.getState())){
        			if(yopresponse.getError() != null)
        			result.put("errorcode",yopresponse.getError().getCode());
        			result.put("errormsg",yopresponse.getError().getMessage());
        			System.out.println("系统处理异常结果："+result);
       			return result;
        		}
        		//成功则进行相关处理
        		if (yopresponse.getStringResult() != null) {
        			result = parseResponse(yopresponse.getStringResult());
        		
       			System.out.println("yopresponse.getStringResult: "+result);

       		}
        		
      		return result;
        	}
        
        

        	//将获取到的yopresponse转换成json格式
        	public static Map<String, String> parseResponse(String yopresponse){
        		
        		Map<String,String> jsonMap  = new HashMap<>();
        		jsonMap	= JSON.parseObject(yopresponse, 
        				new TypeReference<TreeMap<String,String>>() {});
        		System.out.println("将结果yopresponse转化为map格式之后: "+jsonMap);
        		return jsonMap;
        	}
       
        }
        

