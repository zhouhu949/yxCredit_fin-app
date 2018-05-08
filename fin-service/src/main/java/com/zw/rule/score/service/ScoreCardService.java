package com.zw.rule.score.service;

import com.zw.rule.score.po.ScoreCard;

import java.util.Map;

/**
 * Created by Administrator on 2017/7/7 0007.
 */
public interface ScoreCardService {
    ScoreCard getByIdCard(String idCard);

    Map getRefuse(String idCard);
}
