package com.zw.jiguangNew.jpush.api.push;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

public class PushResult extends BaseResult {

    private static final long serialVersionUID = 93783137655776743L;

    @Expose public long msg_id;
    @Expose public int sendno;
    @Expose public int statusCode;

    
}

