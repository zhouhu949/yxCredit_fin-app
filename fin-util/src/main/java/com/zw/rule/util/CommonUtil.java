package com.zw.rule.util;


import com.zw.base.util.MD5Utils;
import com.zw.rule.entity.RECommonRequest;

/**
 * Created by Administrator on 2017/6/23 0023.
 */
public class CommonUtil {
    /**
     * 获取sign
     * @param
     * @param token
     * @return String
     */
    public static String generateSign(RECommonRequest request, String token) throws Exception {
        StringBuffer sign = new StringBuffer();
        sign.append(request.getAct());
        sign.append(",");
        sign.append(request.getTs());
        sign.append(",");
        sign.append(request.getNonce());
        sign.append(",");
        sign.append(request.getPid());
        sign.append(",");
        sign.append(request.getUid());
        sign.append(",");
        sign.append(token == null ? request.getToken() : token);
        return MD5Utils.GetMD5Code(sign.toString());
    }
}


