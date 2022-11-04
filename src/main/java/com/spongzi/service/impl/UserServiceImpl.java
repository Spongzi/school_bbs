package com.spongzi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spongzi.exception.BlogException;
import com.spongzi.exception.BlogExceptionEnum;
import com.spongzi.service.UserService;
import com.spongzi.domain.User;
import com.spongzi.mapper.UserMapper;
import com.spongzi.utlis.TokenUtil;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author spongzi
 * @description 针对表【user(用户表信息)】的数据库操作Service实现
 * @createDate 2022-11-04 12:31:48
 */
@Log
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public String loginByPassword(String username, String phone, String password) {
        if (StringUtils.isEmpty(password)) {
            throw new BlogException(BlogExceptionEnum.PARAM_ERROR);
        }
        log.info("test");
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        log.info(String.valueOf(StringUtils.isBlank(username)));
        userWrapper.eq(!StringUtils.isBlank(username), User::getUsername, username);
        userWrapper.eq(!StringUtils.isBlank(phone), User::getPhone, phone);
        User user = userMapper.selectOne(userWrapper);
        if (user == null) {
            throw new BlogException(BlogExceptionEnum.USER_NOT_EXIST);
        }
        log.info("user info" + user.toString());

        String dbPassword = user.getPassword();

        // 对密码进行加密
        password = DigestUtils.md5DigestAsHex((password + password + user.getUsername()).getBytes(StandardCharsets.UTF_8));

        // 判断密码是否和数据库查询出来的密码相同
        if (!dbPassword.equals(password)) {
            throw new BlogException(BlogExceptionEnum.USER_PASSWORD_ERROR);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        return TokenUtil.getToken(map);
    }
}




