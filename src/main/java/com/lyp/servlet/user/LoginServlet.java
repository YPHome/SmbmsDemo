package com.lyp.servlet.user;

import com.lyp.pojo.User;
import com.lyp.service.user.UserServiceImpl;
import com.lyp.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入loginServlet");
        //  获取用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
         //和数据库中的密码对比
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword);
        if(user!=null){//查有此人
            req.getSession().setAttribute(Constants.USER_SESSION,user);
            resp.sendRedirect("jsp/frame.jsp");
        }else{//查无此人
            req.setAttribute("error","用户名密码不正确");
            req.getRequestDispatcher("login.jsp").forward(req,resp);

        }



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
