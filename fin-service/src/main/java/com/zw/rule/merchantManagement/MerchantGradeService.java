package com.zw.rule.merchantManagement;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/4.
 * 商户分级设置的服务层接口
 */
public interface MerchantGradeService {
     List<MerchantGrade> getAllMerchantGrades(Map map);
     int addMerchantGrade(MerchantGrade grade);
     int sartOrStopGrade(Map map);
     MerchantGrade getOneGradeById(Map map);
     int changeOneGradeById(MerchantGrade grade);

}
