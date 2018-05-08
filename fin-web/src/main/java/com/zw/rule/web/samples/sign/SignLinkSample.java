package com.zw.rule.web.samples.sign;

import com.junziqian.api.bean.Signatory;
import com.junziqian.api.common.IdentityType;
import com.junziqian.api.request.SignLinkRequest;
import com.junziqian.api.response.SignLinkResponse;
import com.junziqian.api.util.LogUtils;
import com.zw.rule.web.samples.JunziqianClientInit;

/**
 * Created by wlinguo on 2016-05-16.
 */
public class SignLinkSample extends JunziqianClientInit {
    public static void main(String[] args) {
        SignLinkRequest request = new SignLinkRequest();
        request.setApplyNo("APL930787511582736384");

        Signatory signatory = new Signatory();
        signatory.setFullName("张涛");
        signatory.setIdentityCard("342422199202218077");
        signatory.setSignatoryIdentityType(IdentityType.IDCARD);
        request.setSignatory(signatory);

        SignLinkResponse response = getClient("","","").signLink(request);
        LogUtils.logResponse(response);
    }

}
