package com.zsm.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Author : ZSM
 * Time :  2024/06/29
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String originalUrl = uri + (queryString == null ? "" : "?" + queryString);
        session.setAttribute("originalUrl", originalUrl);
        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
