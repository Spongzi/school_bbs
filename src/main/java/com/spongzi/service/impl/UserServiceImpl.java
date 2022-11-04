package com.spongzi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spongzi.exception.BlogException;
import com.spongzi.exception.BlogExceptionEnum;
import com.spongzi.service.UserService;
import com.spongzi.domain.User;
import com.spongzi.mapper.UserMapper;
import com.spongzi.utlis.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author spongzi
 * @description 针对表【user(用户表信息)】的数据库操作Service实现
 * @createDate 2022-11-04 12:31:48
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public String login(String username, String password) {
        if (StringUtils.isAllBlank(username, password)) {
            throw new BlogException(BlogExceptionEnum.PARAM_ERROR);
        }
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(userWrapper);
        String dbPassword = user.getPassword();
        if (!dbPassword.equals(password)) {
            throw new BlogException(BlogExceptionEnum.USER_PASSWORD_ERROR);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        return TokenUtil.getToken(map);
    }
}




