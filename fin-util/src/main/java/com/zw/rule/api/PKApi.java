package com.zw.rule.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zw.base.util.MD5Utils;
import com.zw.rule.pk.PKTaskEntity;
import com.zw.rule.util.HttpClientUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by WeiHong on 2017/6/30.
 */
public class PKApi {

    private static final String URL = "http://120.76.78.6:5072/surface/pushTask/";

    private static final String AUTHORIZATION = "b6c7fcc4db8a4639cfbfc9b2dd0e554c";

    public static String sendTask(PKTaskEntity entity) throws Exception {
        return HttpClientUtils.doPost(URL, JSON.toJSONString(entity), MD5Utils.GetMD5Code(AUTHORIZATION));
    }

}
