package com.zw.rule.score.service.impl;

import com.zw.rule.mapper.scoreCard.ScoreCardMapper;
import com.zw.rule.score.po.ScoreCard;
import com.zw.rule.score.service.ScoreCardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年07月07日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Administrator <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
@Service
public class ScoreCardServiceImpl implements ScoreCardService{
    @Resource
    private ScoreCardMapper scoreCardMapper;
    @Override
    public ScoreCard getByIdCard(String idCard) {
        ScoreCard scoreCard = scoreCardMapper.getByIdCard(idCard);
        if(scoreCard!=null){
            return scoreCard;
        }
        return null;
    }

    @Override
    public Map getRefuse(String idCard) {
        Map map = scoreCardMapper.getRefuse(idCard);
        if(map!=null){
            return map;
        }
        return null;
    }
}