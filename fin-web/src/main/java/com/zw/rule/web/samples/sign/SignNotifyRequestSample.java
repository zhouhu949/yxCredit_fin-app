package com.zw.rule.web.samples.sign;

import com.junziqian.api.bean.Signatory;
import com.junziqian.api.common.IdentityType;
import com.junziqian.api.request.SignNotifyRequest;
import com.junziqian.api.util.LogUtils;
import com.zw.rule.web.samples.JunziqianClientInit;
import org.ebaoquan.rop.response.RopResponse;

/**
 * 签约提醒ex.
 * @author yfx
 */
public class SignNotifyRequestSample extends JunziqianClientInit {

	public static void main(String[] args) {
		SignNotifyRequest request = new SignNotifyRequest();
		request.setApplyNo("APL861876085455986688");
		request.setSignNotifyType(SignNotifyRequest.NOTIFYTYPE_SIGN_EXPIRE_FIX);
		Signatory signatory = new Signatory();
		signatory.setFullName("易凡翔");
		signatory.setIdentityCard("500240198704146355");
		signatory.setSignatoryIdentityType(IdentityType.IDCARD);
		request.setSignatory(signatory);
		RopResponse response = getClient("","","").signNotify(request);
		LogUtils.logResponse(response);
	}
}
