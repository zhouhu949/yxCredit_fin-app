package com.zw.jiguangNew.jpush.api;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.zw.jiguangNew.api.ErrorCodeEnum;
import com.zw.jiguangNew.jpush.api.push.PushResult;
import com.zw.jiguangNew.jpush.api.push.model.Options;
import com.zw.jiguangNew.jpush.api.push.model.Platform;
import com.zw.jiguangNew.jpush.api.push.model.PushPayload;
import com.zw.jiguangNew.jpush.api.push.model.audience.Audience;
import com.zw.jiguangNew.jpush.api.push.model.notification.AndroidNotification;
import com.zw.jiguangNew.jpush.api.push.model.notification.IosNotification;
import com.zw.jiguangNew.jpush.api.push.model.notification.Notification;


/******************************************************
 *Copyrights @ 2016，zhiwang  Co., Ltd.
 *         项目名称 快捷易贷
 *All rights reserved.
 *
 *Filename：
 *		文件名称  app推送测试
 *Description：
 *		文件描述 v3版本，v2版本因为在2015年就停止使用了，由于安卓sdk的升级，所以造成一些无法修复的bug
 *Author:
 *		填入作者姓名 张涛
 *Finished：
 *		2017年1月11日
 ********************************************************/
public class JPushClientUtil {
    private static final String appKey ="369c66232dfef521057e4a37";////�������466f7032ac604e02fb7bda89
    private static final String masterSecret = "91389872ee22828305b2b086";//���ÿ��Ӧ�ö���Ӧһ��masterSecret

    public static void main(String[] args) throws Exception {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());  
        // For push, all you need do is to build PushPayload object.
        String title ="测试";
        String message = "1111111111";
        String phone = "141fe1da9eafb6dfbe0";
        PushPayload payload = push_ios_android_All(title,message,phone);
        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.println(result.msg_id);
            System.out.println(result);

        } catch (APIConnectionException e) {

        } catch (APIRequestException e) {
        }
    }
    public static boolean regId (String title, String message, String phone) throws APIConnectionException, APIRequestException {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
        PushPayload payload = push_ios_android_All(title,message,phone);
        PushResult result = jpushClient.sendPush(payload);
        if(result.statusCode == ErrorCodeEnum.NOERROR.value()){
            return true;
        }else {
            return false;
        }
    }
   //ios&&安卓发送推送消息
    public static PushPayload push_ios_android_All(String title, String message, String phone) {
        return PushPayload.newBuilder().setPlatform(Platform.all()) .setAudience(Audience.registrationId(phone))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(title)
                                .setAlert(message)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(message)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                .setApnsProduction(false).build()).build();
    }
}
