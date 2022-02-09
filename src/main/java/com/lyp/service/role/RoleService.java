package com.lyp.service.role;

import com.lyp.pojo.Role;

import java.sql.Connection;
import java.util.List;

public interface RoleService {
    //获取角色信息
    public List<Role> getRoleList();
}
