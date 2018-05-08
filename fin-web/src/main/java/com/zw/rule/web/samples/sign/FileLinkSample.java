package com.zw.rule.web.samples.sign;

import com.junziqian.api.request.FileLinkRequest;
import com.junziqian.api.response.SignLinkResponse;
import com.junziqian.api.util.LogUtils;
import com.zw.rule.web.samples.JunziqianClientInit;

/**
 * Created by wlinguo on 2016-05-16.
 */
public class FileLinkSample extends JunziqianClientInit {
    public static void main(String[] args) {
    	FileLinkRequest request = new FileLinkRequest();
        request.setApplyNo("APL930776677569675264");
        SignLinkResponse response = getClient("","","").fileLink(request);
        LogUtils.logResponse(response);
    }
}
