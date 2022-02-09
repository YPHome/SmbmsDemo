package com.lyp.dao.role;

import com.lyp.dao.BaseDao;
import com.lyp.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao{
    @Override
    public List<Role> getRoleList(Connection connection) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Role> roleList = new ArrayList<Role>();
        if (connection!=null){
            StringBuffer sql = new StringBuffer();
            List<Object> list = new ArrayList<Object>();

            sql.append("select * from smbms_role ");
            Object[] params = list.toArray();
            System.out.println(sql.toString());
            //System.out.println(currentPageNo);
            //System.out.println(pageSize);

            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            while(rs.next()){
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setRoleCode(rs.getString("roleCode"));
                role.setRoleName(rs.getString("roleName"));
                //role.setCreateBy(rs.getInt("createdBy"));
                //role.setCreationDate(rs.getDate("creationDate"));
                //role.setModifyBy(rs.getInt("modifyBy"));
                // role.setModifyDate(rs.getDate("modifyDate"));
                roleList.add(role);
            }
            BaseDao.closeResource(null,pstm,rs);
        }

        return roleList;
    }
}
