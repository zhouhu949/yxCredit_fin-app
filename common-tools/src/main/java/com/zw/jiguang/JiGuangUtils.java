package com.zw.jiguang;


import com.zw.jiguang.api.*;
import com.zw.jiguang.api.JPushClient;

import java.util.HashMap;
import java.util.Map;

/******************************************************
 *Copyrights @ 2016，zhiwang  Co., Ltd.
 *         项目名称 消费金融
 *All rights reserved.
 *
 *Filename：
 *		文件名称  app推送测试
 *Description：
 *		文件描述 推送测试
 *Author:
 *		填入作者姓名 倪帅
 *Finished：
 *		2016年11月8日
 ********************************************************/
public class JiGuangUtils {
    //在极光注册上传应用的 appKey 和 masterSecret
    private static final String appKey = "4baa7381165a330cdae83876";////必填，例如466f7032ac604e02fb7bda89

    private static final String masterSecret = "881f3f40a14d97d4703a7527";//必填，每个应用都对应一个masterSecret

    private static JPushClient jpush = null;

    private static final int MAX_LENGTH = 47;
    /*
     * 保存离线的时长。秒为单位。最多支持10天（864000秒）。
     * 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
     * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒
     */
    private static long timeToLive = 60 * 60 * 24 * 2;

    /**
     * 向所有用户推送信息
     *
     * @param title   推送的标题
     * @param message 推送的内容
     * @return boolean false 推送失败 true 推送成功
     */
    public static boolean all(String title, String message) {
        jpush = new JPushClient(masterSecret, appKey, timeToLive);
        return send(title, message);//发送
    }

    /**
     * 向所用ios用户发送推送
     *
     * @param title   推送的标题
     * @param message 推送的内容
     * @return boolean false 推送失败 true 推送成功
     */
    public static boolean ios_all(String title, String message) {
        jpush = new JPushClient(masterSecret, appKey, timeToLive, DeviceEnum.IOS);
        return send(title, message);//发送
    }

    /**
     * 向所用android用户发送推送
     *
     * @param title   推送的标题
     * @param message 推送的内容
     * @return boolean false 推送失败 true 推送成功
     */
    public static boolean android_all(String title, String message) {
        jpush = new JPushClient(masterSecret, appKey, timeToLive, DeviceEnum.Android);
        return send(title, message);//发送
    }

    /**
     * 根据别名向单个用户发送推送
     *
     * @param title   推送的标题
     * @param message 推送的内容
     * @return boolean false 推送失败 true 推送成功
     */
    public static boolean alias(String title, String message, String phone) {
        jpush = new JPushClient(masterSecret, appKey, timeToLive);
        return send_alias(title, message, phone);//发送
    }

    public static void main(String[] args) {
        //jpush = new JPushClient(masterSecret, appKey, timeToLive,DeviceEnum.IOS);
        System.out.println(JiGuangUtils.all("测试", "郭庆是都比"));
        //testSend("测试","hehehhehe");
//        System.out.println(JiGuangUtils.alias("测试", "郭庆是都比", "160a3797c83453acba4"));
    }

    private static boolean send(String title, String message) {
        // 在实际业务中，建议 sendNo 是一个你自己的业务可以处理的一个自增数字。
        // 除非需要覆盖，请确保不要重复使用。详情请参考 API 文档相关说明。
        Integer num = getRandomSendNo();
        String sendNo = num.toString();
        String msgTitle = title;
        if (message.indexOf("<") > -1 && message.indexOf(">") > -1) {
            message = message.replaceAll("<(!|/)?(.|\n)*?>", "");
            String[] arr = message.split("。");
            message = arr[0] + "。";
        }
        if (message.length() > MAX_LENGTH) {
            message = message.substring(0, MAX_LENGTH) + "...";
        }
        String msgContent = message;
            /*
			 * IOS设备扩展参数,
			 * 设置badge，设置声音
			 */
        Map<String, Object> extra = new HashMap<String, Object>();
        IOSExtra iosExtra = new IOSExtra(0, "WindowsLogonSound.wav");
        extra.put("ios", iosExtra);
        //IOS和安卓一起
        MessageResult msgResult = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent, 0, extra);
        //对所有用户发送通知, 更多方法请参考文档
        //	MessageResult msgResult = jpush.sendCustomMessageWithAppKey(sendNo,msgTitle, msgContent);
        if (null != msgResult) {
            if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
                return true;
            }
        }
        return false;
    }

    private static boolean send_alias(String title, String message, String phone) {
        // 在实际业务中，建议 sendNo 是一个你自己的业务可以处理的一个自增数字。
        // 除非需要覆盖，请确保不要重复使用。详情请参考 API 文档相关说明。
        Integer num = getRandomSendNo();
        String sendNo = num.toString();
        String msgTitle = title;
        if (message.indexOf("<") > -1 && message.indexOf(">") > -1) {
            message = message.replaceAll("<(!|/)?(.|\n)*?>", "");
            String[] arr = message.split("。");
            message = arr[0] + "。";
        }
        if (message.length() > MAX_LENGTH) {
            message = message.substring(0, MAX_LENGTH) + "...";
        }
        String msgContent = message;
			/*
			 * IOS设备扩展参数,
			 * 设置badge，设置声音
			 */
        Map<String, Object> extra = new HashMap<String, Object>();
        IOSExtra iosExtra = new IOSExtra(0, "WindowsLogonSound.wav");
        extra.put("ios", iosExtra);
        //IOS和安卓一起
        MessageResult msgResult = jpush.sendNotificationWithAlias(sendNo, phone, msgTitle, msgContent, 0, extra);

        //对所有用户发送通知, 更多方法请参考文档
        //	MessageResult msgResult = jpush.sendCustomMessageWithAppKey(sendNo,msgTitle, msgContent);
        if (null != msgResult) {
            if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
                return true;
            }
        }
        return false;
    }

    public static final int MAX = Integer.MAX_VALUE;
    public static final int MIN = (int) MAX / 2;

    /**
     * 保持 sendNo 的唯一性是有必要的
     * It is very important to keep sendNo unique.
     *
     * @return sendNo
     */
    public static int getRandomSendNo() {
        return (int) (MIN + Math.random() * (MAX - MIN));
    }
}
