package com.zw.rule.service;


import com.zw.rule.po.Role;
import com.zw.rule.mybatis.ParamFilter;

import java.util.List;
import java.util.Map;

public interface RoleService{

    void add(Role role);

    void delete(List<String> roleIds);

    void update(Role role);

    List<Role> getList(ParamFilter param);

    Role getByRoleId(String roleId);

    List <Role> getByRoleName(Map map);

    List getRoleMap(long userId,Integer organId);
}
