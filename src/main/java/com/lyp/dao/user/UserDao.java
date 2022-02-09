package com.lyp.dao.user;

import com.lyp.pojo.Role;
import com.lyp.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    //得到登陆的用户
    public User getLoginUser(Connection connection, String userCode) throws SQLException;
    //修改当前用户密码
    public int updatePwd(Connection connection, String userCode, String password) throws SQLException;
    //查询用户总数
    public int getUserCount(Connection connection, String userName, int userRole)throws SQLException;
    //查询用户列表
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize)throws SQLException;


}
