package com.zw.jiguangNew;


import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.zw.jiguangNew.api.ErrorCodeEnum;
import com.zw.jiguangNew.jpush.api.JPushClient;
import com.zw.jiguangNew.jpush.api.push.PushResult;
import com.zw.jiguangNew.jpush.api.push.model.Options;
import com.zw.jiguangNew.jpush.api.push.model.Platform;
import com.zw.jiguangNew.jpush.api.push.model.PushPayload;
import com.zw.jiguangNew.jpush.api.push.model.audience.Audience;
import com.zw.jiguangNew.jpush.api.push.model.notification.AndroidNotification;
import com.zw.jiguangNew.jpush.api.push.model.notification.IosNotification;
import com.zw.jiguangNew.jpush.api.push.model.notification.Notification;
import org.apache.log4j.Logger;

/******************************************************
 *Copyrights @ 2016，zhiwang  Co., Ltd.
 *         项目名称 秒付金服
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
public class JiGuangUtils {
    private static final Logger log = Logger.getLogger(JiGuangUtils.class);
    //在极光注册上传应用的 appKey 和 masterSecret
//    private static final String appKey = "ae915ff2d5449b6f76cf9a65";////必填，369c66232dfef521057e4a37
//
//    private static final String masterSecret = "589c4c394e889ada5d709026";//必填，每个应用都对应一个masterSecret
    /**
     * 根据别名向单个用户发送推送
     *
     * @param title   推送的标题
     * @param message 推送的内容
     * @return boolean false 推送失败 true 推送成功
     */
    public static boolean alias(String appKey, String masterSecret, String title, String message, String phone) throws APIConnectionException, APIRequestException {
        PushPayload payload = push_ios_android_All(title,message,phone);
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
        try {
            PushResult result = jpushClient.sendPush(payload);
            if(result.statusCode == ErrorCodeEnum.NOERROR.value()){
                return true;
            }else {
                return false;
            }
        }catch (APIConnectionException e) {
            log.error("链接异常", e);
            return false;
        }catch (APIRequestException e) {
            log.error("请求异常", e);
            log.info("请求状态: " + e.getStatus());
            log.info("失败CODE: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
            log.info("Msg ID: " + e.getMsgId());
            return false;
        }
    }
    //ios&&安卓发送推送消息(v3版本java SDK)
    public static PushPayload push_ios_android_All(String title, String message, String phone) {
        return PushPayload.newBuilder().setPlatform(Platform.all()) .setAudience(Audience.alias(phone))
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
                        .setApnsProduction(false)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(3600)//消息在JPush服务器的失效时间（测试使用参数）
                        .build()).build();
    }
    public static void main(String[] args) throws APIConnectionException, APIRequestException {
        System.out.println(JiGuangUtils.alias("ae915ff2d5449b6f76cf9a65", "589c4c394e889ada5d709026", "测试", "8888888888888888888888888888888888", "13065ffa4e02277c3a9"));//141fe1da9eafb6dfbe0
    }
}
