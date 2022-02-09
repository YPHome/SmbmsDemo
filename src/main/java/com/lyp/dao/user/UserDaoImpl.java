package com.lyp.dao.user;

import com.lyp.dao.BaseDao;
import com.lyp.pojo.Role;
import com.lyp.pojo.User;
import com.mysql.jdbc.StringUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{

    @Override
    public User getLoginUser(Connection connection, String userCode) throws SQLException {

        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;

        if(connection!=null) {
            String sql = "select * from smbms_user where userCode = ?";
            Object[] params = {userCode};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreationDate(rs.getDate("creationDate"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getDate("modifyDate"));
            }
        BaseDao.closeResource(null,pstm,rs);

        }
        return user;
    }

    @Override
    public int updatePwd(Connection connection, String userCode, String userPassword) throws SQLException {
        PreparedStatement pstm = null;
        User user = null;
        int execute =0;
        if(connection!=null) {
            String sql = "update smbms_user set userPassword = ? where userCode = ?";
            Object[] params = {userPassword,userCode};
            execute = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null,pstm,null);

        }
        return execute;
    }

    @Override
    public int getUserCount(Connection connection, String userName, int userRole) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;

        if (connection!=null){
            StringBuffer sql = new StringBuffer();
            ArrayList<Object> list = new ArrayList<Object>();//存放数据

            sql.append("select count(1) as count from smbms_user u, smbms_role r where u.userRole = r.id");
            if (!StringUtils.isNullOrEmpty(userName)){
                sql.append(" and u.userName like ?");
                list.add("%"+userName+"%");//index 0含有userRole
            }
            if (userRole>0){
                sql.append(" and u.userRole = ?");
                list.add(userRole);//index 1  含有userRole

            }
            Object[] params = list.toArray();
            System.out.println(sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            if(rs.next()){
                count = rs.getInt("count");
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return count;
    }


    @Override
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<>();


        if (connection!=null){
            StringBuffer sql = new StringBuffer();
            List<Object> list = new ArrayList<Object>();

            sql.append("select u.*, r.roleName as userRoleName from smbms_user u, smbms_role r where u.userRole = r.id");

            if (!StringUtils.isNullOrEmpty(userName)){
                sql.append(" and u.userName like ?");
                list.add("%"+userName+"%");//index 0含有userRole
            }
            if (userRole>0){
                sql.append(" and u.userRole = ?");
                list.add(userRole);//index 1  含有userRole

            }
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println(sql.toString());
            //System.out.println(currentPageNo);
            //System.out.println(pageSize);

            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            while(rs.next()){
                User userl = new User();
                userl.setId(rs.getInt("id"));
                userl.setUserCode(rs.getString("userCode"));
                userl.setUserName(rs.getString("userName"));
                userl.setUserPassword(rs.getString("userPassword"));
                userl.setGender(rs.getInt("gender"));
                userl.setBirthday(rs.getDate("birthday"));
                userl.setPhone(rs.getString("phone"));
                userl.setAddress(rs.getString("address"));
                userl.setUserRole(rs.getInt("userRole"));
                userl.setCreationDate(rs.getDate("creationDate"));
                userl.setCreatedBy(rs.getInt("createdBy"));
                userl.setModifyBy(rs.getInt("modifyBy"));
                userl.setModifyDate(rs.getDate("modifyDate"));
                userl.setUserRoleName(rs.getString("userRoleName"));
                userList.add(userl);
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return userList;
    }


}

