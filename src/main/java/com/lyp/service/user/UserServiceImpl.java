package com.lyp.service.user;

import com.lyp.dao.BaseDao;
import com.lyp.dao.user.UserDao;
import com.lyp.dao.user.UserDaoImpl;
import com.lyp.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService{
    //引入Dao层
    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    @Override
    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;
        connection = BaseDao.getConnection();
        try {
            user = userDao.getLoginUser(connection,userCode);
            if (!user.getUserPassword().equals(password)){
                user = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }finally{
            BaseDao.closeResource(connection,null,null);
        }

        return user;
    }


    @Override
    public boolean updatePwd(String userCode, String userPassword) {
         Connection connection = null;
         boolean flag = false;

         //修改密码
        try {
            connection = BaseDao.getConnection();
            if(userDao. updatePwd(connection,userCode,userPassword)>0){
                flag = true;
            }
        } catch (SQLException e) {

        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }

    @Override
    public int getUserCount(String userName, int userRole) {
        Connection connection = null;
        int count = 0;
        try {
            connection = BaseDao.getConnection();
            count = userDao.getUserCount(connection, userName, userRole);


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }

        return count;
    }


    @Override
    public List<User> getUserList(String userName, int userRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User> userList = null;
        try {
            connection = BaseDao.getConnection();
            userList = userDao.getUserList(connection, userName, userRole, currentPageNo, pageSize);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }

        return userList;
    }

    @Test
    public void test(){
        UserServiceImpl userService = new UserServiceImpl();
  //      int userCount = userService.getUserCount(null,1);
        List<User> list = null;
        list = userService.getUserList(null,1,1,5);
        System.out.println(list.toString());

    }
}
