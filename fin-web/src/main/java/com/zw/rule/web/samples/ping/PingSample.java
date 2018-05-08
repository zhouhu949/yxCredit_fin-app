package com.zw.rule.web.samples.ping;

import com.junziqian.api.request.PingRequest;
import com.junziqian.api.response.PingResponse;
import com.junziqian.api.util.LogUtils;
import com.zw.rule.web.samples.JunziqianClientInit;


/**
 * Created by wlinguo on 5/11/2016.
 */
public class PingSample extends JunziqianClientInit {
    public static void main(String[] args) {
        int suc=0;
        for(int i=0;i<100;i++){
            if(i>0&&i%24==0){
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                }
            }
            PingRequest request = new PingRequest();
            PingResponse response = getClient("","","").ping(request);
            if(response.isSuccess()){
                suc++;
            }
            LogUtils.logResponse(response);
        }
        System.out.println(suc);
    }
}

