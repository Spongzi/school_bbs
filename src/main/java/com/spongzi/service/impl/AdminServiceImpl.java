package com.spongzi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spongzi.domain.User;
import com.spongzi.domain.dto.UserLoginDto;
import com.spongzi.domain.vo.UserVo;
import com.spongzi.exception.BlogException;
import com.spongzi.exception.BlogExceptionEnum;
import com.spongzi.mapper.UserMapper;
import com.spongzi.service.AdminService;
import com.spongzi.service.UserService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.stream.Collectors;

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
    public Page<UserVo> selectUser(String page, String pagesize, String username, String gender, String status) {
        Page<User> userPage = new Page<>(Integer.parseInt(page), Integer.parseInt(pagesize));
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isBlank(username), User::getUsername, username);
        queryWrapper.eq(!StringUtils.isBlank(gender), User::getGender, gender);
        queryWrapper.eq(!StringUtils.isBlank(status), User::getUserStatus, status);
        userMapper.selectPage(userPage, queryWrapper);
        Page<UserVo> userVoPage = new Page<>();

        // 进行去敏操作
        List<User> records = userPage.getRecords();
        List<UserVo> userVoList = records.stream().map(user -> userService.getSafeUser(user)).collect(Collectors.toList());
        userVoPage.setTotal(userPage.getTotal());
        userVoPage.setCurrent(userPage.getCurrent());
        userVoPage.setSize(userPage.getSize());
        userVoPage.setPages(userPage.getPages());
        userVoPage.setRecords(userVoList);
        userVoPage.setOptimizeCountSql(userPage.optimizeCountSql());
        userVoPage.setSearchCount(userPage.searchCount());
        userVoPage.setCountId(userPage.countId());
        userVoPage.setMaxLimit(userPage.maxLimit());
        return userVoPage;
    }

}
