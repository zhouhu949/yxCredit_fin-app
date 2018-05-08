package com.zw.rule.mapper.wechat;

import com.zw.rule.customer.po.AppUser;
import org.apache.ibatis.annotations.Param;


public interface WechatUserMapper {
   public AppUser findByTel(@Param("tel")String tel);

   public int addUser(AppUser appUser);

   public int updatePwdByTel(AppUser appUser);
}
