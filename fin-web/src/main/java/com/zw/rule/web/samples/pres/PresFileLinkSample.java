package com.zw.rule.web.samples.pres;

import com.junziqian.api.bean.Signatory;
import com.junziqian.api.common.IdentityType;
import com.junziqian.api.request.PresFileLinkRequest;
import com.junziqian.api.response.SignLinkResponse;
import com.junziqian.api.util.LogUtils;
import com.zw.rule.web.samples.JunziqianClientInit;

import java.util.Map;

/**
 * 保全后的文件下载地址
 * @author yfx
 */
public class PresFileLinkSample extends JunziqianClientInit {
	public static String getJunziqian(Map map) {
		PresFileLinkRequest request = new PresFileLinkRequest();
	     request.setApplyNo(map.get("contractNo").toString());

	     Signatory signatory = new Signatory();
	     signatory.setFullName(map.get("cusName").toString());
	     signatory.setIdentityCard(map.get("cusCard").toString());
	     signatory.setSignatoryIdentityType(IdentityType.IDCARD);
	     request.setSignatory(signatory);
		 String serviceUrl = map.get("services_url").toString();
		 String appSecrete = map.get("appSecrete").toString();
		 String appKey = map.get("appKey").toString();
	     SignLinkResponse response = getClient(serviceUrl,appKey,appSecrete).presFileLink(request);
	     LogUtils.logResponse(response);
	     return response.getLink();
	}
}
