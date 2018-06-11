package com.zw.rule.util;

import com.google.common.collect.Sets;

import java.util.Set;

public class Constants {
    public static final String ALGORITHM_NAME = "SHA-256"; // 加密算法
    public static final int HASH_ITERATIONS = 2; // 加密次数

    public static final Set<Integer> STATUS_SET =Sets.newHashSet(0,1);

    public static  final String  SMS_SUCCESS_STATE = "1";

    public static  final String  SMS_FAILURE_STATE = "0";

    public static  final String  ENABLE_STATE = "1";

    public static  final String  DISENABLE_STATE = "0";

    public static final String ORDER_AUDIT_PASS_STATE = "3";//审核通过 -> 待签约

    public static final String ORDER_AUDIT_FAILURE_STATE = "8";//审核拒绝

    public static final String ORDER_AUDIT_LOAN_STATE = "5";//放款通过 -> 待还款

    // --------------------   app_message  state 状态 '0 未读  1 失效  2 已读' -------------------------
    public static final String MESSAGE_UNREAD_STATE = "0";//状态 '0 未读';
    public static final String MESSAGE_READ_FAILURE_STATE = "1";//状态 '1 失效';
    public static final String MESSAGE_READED_STATE = "2";//状态 '2 已读;

    public static final String MESSAGE_PUSH_SUCCESS_STATE = "0";//状态 '0:成功;
    public static final String MESSAGE_PUSH_FAILURE_STATE = "1";//状态 '1:失败;
}
