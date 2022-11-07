package com.spongzi.interceptor;

import com.spongzi.domain.User;
import com.spongzi.exception.BlogException;
import com.spongzi.exception.BlogExceptionEnum;
import com.spongzi.utlis.TokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static com.spongzi.constant.UserConstant.TOKEN_REDIS;

/**
 * 登录拦截器
 *
 * @author spongzi
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 获取请求头
        String token = request.getHeader("Authorization");
        // 2. 使用工具类判断token是否有效
        boolean verifyToken = TokenUtil.verifyToken(token);
        // 3. 如果token失效, 返回状态码401, 拦截
        if (!verifyToken) {
            response.setStatus(401);
            throw new BlogException(BlogExceptionEnum.USER_NOT_LOGIN);
        }
        // 与存在redis中的数据进行对比
        Claims data = TokenUtil.getClaims(token);
        String id1 = data.get("id").toString();
        String redisToken = redisTemplate.opsForValue().get(TOKEN_REDIS + id1);
        if (redisToken == null) {
            throw new BlogException(BlogExceptionEnum.USER_NOT_LOGIN);
        }
        if (!redisToken.equals(token)) {
            throw new BlogException(BlogExceptionEnum.USER_NOT_LOGIN);
        }
        // 4. 如果token正常可用, 方行
        // 解析token获取id, 手机号, 构造user对象
        Claims claims = TokenUtil.getClaims(token);
        String email = (String) claims.get("email");
        String mobile = (String) claims.get("phone");
        Integer id = (Integer) claims.get("id");

        User user = new User();
        user.setId(Long.valueOf(id));
        user.setEmail(email);
        user.setPhone(mobile);

        UserHolder.set(user);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.remove();
    }
}
