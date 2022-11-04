package com.spongzi.interceptor;

import com.spongzi.utlis.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author spongzi
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    public final String LOGIN_URI = "login";
    public final String REGISTER_URI = "register";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        String token = response.getHeader("token");
        //判断当前请求地址是否登录地址
        if (uri.contains(LOGIN_URI) || uri.contains(REGISTER_URI)) {
            //登录请求，直接放行
            return true;
        } else {
            //判断用户是否登录
            if (TokenUtil.verifyToken(token)) {
                //说明已经登录，放行
                return true;
            } else {
                //没有登录，重定向到登录界面
                response.sendRedirect(request.getContextPath() + "/login");
            }
        }
        //默认拦截
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
