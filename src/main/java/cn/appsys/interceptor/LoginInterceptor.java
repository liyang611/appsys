package cn.appsys.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object devUser = request.getSession().getAttribute("devUserSession");
        Object backendUser = request.getSession().getAttribute("userSession");
        if (devUser == null && backendUser == null) {
            response.sendRedirect("/403.jsp");
            return false;
        }
        return true;
    }
}
