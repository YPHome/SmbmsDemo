package com.lyp.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

//操作数据库的公共类
public class BaseDao {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    //静态代码块，类加载初始化
    static {
        Properties properties = new Properties();
        //通过类加载器读取对应的资源
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");

    }

    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    //编写查询公共类

    public static ResultSet execute (Connection connection, PreparedStatement pstm, ResultSet rs,
                                     String sql, Object[] params) throws SQLException {
        pstm = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            pstm.setObject(i+1,params[i]);
        }
        rs = pstm.executeQuery();
        return rs;
    }

    //更新操作

    public static int execute (Connection connection,PreparedStatement pstm,
                               String sql,Object[] params) throws SQLException {
        int updateRows = 0;
        pstm = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            pstm.setObject(i+1,params[i]);
        }
        updateRows = pstm.executeUpdate();
        return updateRows;

    }

    //释放资源

    public static boolean closeResource(Connection connection,PreparedStatement pstm,ResultSet rs){
        boolean flag = true;
        if(rs != null){
            try {
                rs.close();
                rs = null;//GC回收
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                flag = false;
            }
        }
        if(pstm != null){
            try {
                pstm.close();
                pstm = null;//GC回收
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                flag = false;
            }
        }
        if(connection != null){
            try {
                connection.close();
                connection = null;//GC回收
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
}
