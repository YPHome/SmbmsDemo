package com.lyp.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.lyp.pojo.User;
import com.lyp.service.user.UserService;
import com.lyp.service.user.UserServiceImpl;
import com.lyp.util.Constants;
import com.mysql.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method!=null && method.equals("savepwd")){
            this.updatePwd(req,resp);
        }else if (method!=null && method.equals("pwdmodify")){
            this.pwdModify(req,resp);
        }else if (method!=null && method.equals("query")){
            this.query(req, resp);
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
    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String queryUserName = req.getParameter("queryn ame");
        String tmp = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");

        int queryUserRole =0;

        //获取用户列表
        UserServiceImpl userService = new UserServiceImpl();


        if (queryUserName==null){
            queryUserName="";
        }
        if (tmp!=null&&!tmp.equals("")){
            queryUserRole= Integer.parseInt(tmp);
        }


    }
}
