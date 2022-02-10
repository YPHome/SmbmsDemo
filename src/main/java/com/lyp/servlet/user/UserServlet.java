package com.lyp.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.lyp.pojo.Role;
import com.lyp.pojo.User;
import com.lyp.service.role.RoleService;
import com.lyp.service.role.RoleServiceImpl;
import com.lyp.service.user.UserService;
import com.lyp.service.user.UserServiceImpl;
import com.lyp.util.Constants;
import com.lyp.util.PageSupport;
import com.mysql.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String method = req.getParameter("method");
        if (method!=null && method.equals("savepwd")){
            this.updatePwd(req,resp);
        }else if (method!=null && method.equals("pwdmodify")){
            this.pwdModify(req,resp);
        }else if (method!=null && method.equals("query")){
            try {
                this.query(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    //复用修改新密码
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从session里拿用户ID
        Object attribute = req.getSession().getAttribute(Constants.USER_SESSION);
        String newpassword = req.getParameter("newpassword");

        boolean flag = false;
        if (attribute!=null && !StringUtils.isNullOrEmpty(newpassword)){
            UserService userService = new UserServiceImpl();
            flag = userService.updatePwd(((User) attribute).getUserCode(), newpassword);
            if (flag){
                req.setAttribute("message","修改密码成功请使用新密码重新登陆");
                //移除当前session
                req.getSession().removeAttribute(Constants.USER_SESSION);

            }else{
                req.setAttribute("message","修改密码失败");
            }

        }else{
            req.setAttribute("message","新密码有问题");
        }
        req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);
    }
    public void pwdModify(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Object attribute = req.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");
        //万能的map
        Map<String, String> resultMap = new HashMap<String,String>();
        if(attribute==null){
            resultMap.put("result","sessionerror");//session过期
        }else if(StringUtils.isNullOrEmpty(oldpassword)){
            resultMap.put("result","error");//session密码为空
        }else{
            String userPassword = ((User)attribute).getUserPassword();
            System.out.println(userPassword);
            if (oldpassword.equals(userPassword)){
                resultMap.put("result","true");//原密码=输入的旧密码
            }else{
                resultMap.put("result","false");
            }
        }
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(JSONArray.toJSONString(resultMap));//阿里巴巴fastjson 转换格式
        writer.flush();
        writer.close();

    }
    public void query(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //从前端获取数据
        String queryUserName = req.getParameter("queryname");
        String temp = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");
        int queryUserRole = 0;

        //获取用户列表

        UserService userService = new UserServiceImpl();

        //第一次走页面一定是第一页,页面大小固定的
        List<User> userList = null;
        //设置页面容量
        int pageSize = 5;
        //当前页码
        int currentPageNo = 1;
        /**
         * http://localhost:8090/SMBMS/userlist.do
         * ----queryUserName --NULL
         * http://localhost:8090/SMBMS/userlist.do?queryname=
         * --queryUserName ---""
         */
        System.out.println("queryUserName servlet--------"+queryUserName);
        System.out.println("queryUserRole servlet--------"+queryUserRole);
        System.out.println("query pageIndex--------- > " + pageIndex);
        if(queryUserName == null){
            queryUserName = "";
        }
        if(temp != null && !temp.equals("")){
            queryUserRole = Integer.parseInt(temp);//给查询赋值
        }

        if(pageIndex != null){
            try{
                currentPageNo = Integer.valueOf(pageIndex);
            }catch(NumberFormatException e){
                resp.sendRedirect("error.jsp");
            }
        }
        //总数量（表）
        int totalCount	= userService.getUserCount(queryUserName,queryUserRole);
        //总页数
        PageSupport pages=new PageSupport();

        pages.setCurrentPageNo(currentPageNo);

        pages.setPageSize(pageSize);

        pages.setTotalCount(totalCount);

        int totalPageCount = pages.getTotalPageCount();

        //控制首页和尾页
        if(currentPageNo < 1){
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }


        userList = userService.getUserList(queryUserName,queryUserRole,currentPageNo, pageSize);
        req.setAttribute("userList", userList);
        List<Role> roleList = null;
        RoleService roleService = new RoleServiceImpl();
        roleList = roleService.getRoleList();
        req.setAttribute("roleList", roleList);
        req.setAttribute("queryUserName", queryUserName);
        req.setAttribute("queryUserRole", queryUserRole);
        req.setAttribute("totalPageCount", totalPageCount);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPageNo", currentPageNo);
        req.getRequestDispatcher("userlist.jsp").forward(req, resp);
    }

}
