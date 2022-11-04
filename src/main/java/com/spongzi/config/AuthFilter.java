package com.spongzi.config;

import com.spongzi.exception.BlogException;
import com.spongzi.utlis.TokenUtil;
import org.springframework.core.Ordered;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author spongzi
 */
//@Component
public class AuthFilter implements Filter, Ordered {

    /**
     * 排除url
     */
    private List<String> excludedUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("你好，test");
        excludedUrls.addAll(Arrays.asList("/login", "/register", "/index"));
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestUrl = request.getRequestURL().toString();
        if (excludedUrls.contains(requestUrl)) {
            System.out.println("123");
            filterChain.doFilter(request, response);
        }
        String token = response.getHeader("token");
        if (token == null) {
            throw new BlogException(403, "用户没有登录");
        }
        if (!TokenUtil.verifyToken(token)) {
            throw new BlogException(403, "请重新登录");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}