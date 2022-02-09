package com.lyp.service.user;

import com.lyp.pojo.Role;
import com.lyp.pojo.User;

import java.sql.Connection;
import java.util.List;

public interface UserService {
    //用户登录
    public User login (String userCode, String password);
    //根据用户ID修改密码
    public boolean updatePwd (String userCode, String userPassword);
    //获取用户数量
    public int getUserCount(String userName, int userRole);
    //获取用户信息
    public List<User> getUserList(String userName, int userRole, int currentPageNo, int pageSize);


}
