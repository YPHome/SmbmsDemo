package com.lyp.service.role;

import com.lyp.dao.BaseDao;
import com.lyp.dao.role.RoleDao;
import com.lyp.dao.role.RoleDaoImpl;
import com.lyp.dao.user.UserDao;
import com.lyp.dao.user.UserDaoImpl;
import com.lyp.pojo.Role;
import com.lyp.pojo.User;
import com.lyp.service.user.UserServiceImpl;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoleServiceImpl implements RoleService{
    private RoleDao roleDao;

    public RoleServiceImpl() {
        roleDao = new RoleDaoImpl();
    }
    @Override
    public List<Role> getRoleList() {

        Connection connection = null;
        List<Role> roleList = null;
        try {
            connection = BaseDao.getConnection();
            roleList = roleDao.getRoleList(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }

        return roleList;
    }

    @Test
    public void test(){
        RoleServiceImpl roleService = new RoleServiceImpl();
        //      int userCount = userService.getUserCount(null,1);
        List<Role> list = null;
        list = roleService.getRoleList();
        System.out.println(list.toString());

    }

}
