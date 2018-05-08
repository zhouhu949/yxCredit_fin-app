package com.zw.rule.web.samples.account;

import com.junziqian.api.request.OrganizationAuditStatusRequest;
import com.zw.rule.web.samples.JunziqianClientInit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wlinguo on 2016-07-12.
 */
public class AccountStatusSample extends JunziqianClientInit {

    public static void main(String[] args) {

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 500; i++) {
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    OrganizationAuditStatusRequest request = new OrganizationAuditStatusRequest();
                    request.setEmailOrMobile("xhteor29586@chacuo.net");
//                    OrganizationAuditStatusResponse response = getClient().organizationStatus(request);
//                    LogUtils.logResponse(response);
                }
            });
        }
    }

}
