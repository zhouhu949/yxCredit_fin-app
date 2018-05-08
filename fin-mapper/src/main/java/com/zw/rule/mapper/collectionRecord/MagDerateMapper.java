package com.zw.rule.mapper.collectionRecord;

import com.zw.rule.collectionRecord.po.MagDerate;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/6.
 */
public interface MagDerateMapper {
    List<MagDerate> getMagDerateList(Map map);

    Map getMagDerateDetail(Map map);

    int updateDerate(MagDerate magDerate);
}
