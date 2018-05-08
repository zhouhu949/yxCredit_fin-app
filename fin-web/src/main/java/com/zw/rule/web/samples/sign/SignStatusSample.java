package com.zw.rule.web.samples.sign;

import com.junziqian.api.request.SignStatusRequest;
import com.junziqian.api.response.SignStatusResponse;
import com.junziqian.api.util.LogUtils;
import com.zw.rule.web.samples.JunziqianClientInit;


/**
 * 签字状态查询
 */
public class SignStatusSample extends JunziqianClientInit {
    public static void main(String[] args) {
    	SignStatusRequest request = new SignStatusRequest();
        request.setApplyNo("APL930762214556700672");
        /**
         * Signatory可填可不填 
         * 填写时查询当前约签人的状态
         * 不填写时查询整个签约合同的签约状态
         * */
        /**
        Signatory signatory = new Signatory();
		signatory.setFullName("yfx");
		signatory.setIdentityCard("500240198704146355");
		signatory.setSignatoryIdentityType(IdentityType.IDCARD);
		
		request.setSignatory(signatory);
		*/
		SignStatusResponse response = getClient("","","").signStatus(request);
        LogUtils.logResponse(response);
    }
}
