package com.zw.rule.web.samples;

import com.junziqian.api.JunziqianClient;
import org.ebaoquan.rop.thirdparty.org.apache.commons.lang3.StringUtils;

/**
 * Created by wlinguo on 5/11/2016.
 */
public class JunziqianClientInit {

	public static JunziqianClient getClient(String serviceUrl,String appKey,String appSecret) {
		String SERVICE_URL = serviceUrl;
		String APP_KEY =appKey;
		String APP_SECRET = appSecret;
		if (StringUtils.isBlank(SERVICE_URL)) {
			throw new RuntimeException("SERVICE_URL is null");
		}
		if (StringUtils.isBlank(APP_KEY)) {
			throw new RuntimeException("APP_KEY is null");
		}
		if (StringUtils.isBlank(APP_SECRET)) {
			throw new RuntimeException("APP_SECRET is null");
		}
		JunziqianClient client = new JunziqianClient(SERVICE_URL, APP_KEY, APP_SECRET);
		return client;
	}
}
