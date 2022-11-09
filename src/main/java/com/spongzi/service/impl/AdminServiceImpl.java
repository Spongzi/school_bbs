package com.spongzi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spongzi.domain.User;
import com.spongzi.domain.dto.UserLoginDto;
import com.spongzi.exception.BlogException;
import com.spongzi.exception.BlogExceptionEnum;
import com.spongzi.mapper.UserMapper;
import com.spongzi.service.AdminService;
import com.spongzi.service.UserService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.spongzi.constant.UserConstant.*;

/**
 * 管理服务impl
 *
 * @author spongzi
 * @date 2022/11/07
 */
@Log
@Service
public class AdminServiceImpl extends ServiceImpl<UserMapper, User> implements AdminService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @Override
    public String login(UserLoginDto userLoginDto) {
        if (userLoginDto == null) {
            throw new BlogException(BlogExceptionEnum.PARAM_ERROR);
        }
        String username = userLoginDto.getUsername();
        String email = userLoginDto.getEmail();
        String type = userLoginDto.getType();
        String password = userLoginDto.getPassword();
        if (StringUtils.isBlank(type)) {
            throw new BlogException(BlogExceptionEnum.PARAM_ERROR);
        }
        // 检查权限
        User user = userService.findUser(null, username, email);
        if (!USER_ADMIN.equals(user.getRole())) {
            throw new BlogException(BlogExceptionEnum.USER_PERMISSION_ERROR);
        }
        String token = "";
        // 判断登录方式
        if (USER_LOGIN_BY_PASSWORD.equals(type)) {
            token = userService.loginByPassword(username, email, password);
        }
        if (USER_LOGIN_BY_CHECK_CODE.equals(type)) {
            token = userService.loginByCheckCode(email, password);
        }
        log.info(token);
        return token;
    }

    @Override
    public String deleteByIds(List<Long> ids) {
        userMapper.deleteBatchIds(ids);
        return "删除成功";
    }

    @Override
    public String modifyStatus(String status, List<Long> ids) {
        // 先查询出所有人的信息
        List<User> userList = new ArrayList<>();
        ids.forEach(id -> {
            User user = userMapper.selectById(id);
            if (user == null) {
                throw new BlogException(BlogExceptionEnum.USER_NOT_EXIST);
            }
            // 修改用户中的用户状态
            user.setUserStatus(status);
            userList.add(user);
        });
        userService.updateBatchById(userList);
        return "修改成功";
    }


}
