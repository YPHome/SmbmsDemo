package com.lyp.filter;

import com.lyp.pojo.User;
import com.lyp.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        User user = (User)request.getSession().getAttribute(Constants.USER_SESSION);//从session获取

        if(user==null){//已经被移除或注销或未登录
            response.sendRedirect("../error.jsp");

        }else{
            filterChain.doFilter(req,resp);
        }
    }

    @Override
    public void destroy() {

    }
}
