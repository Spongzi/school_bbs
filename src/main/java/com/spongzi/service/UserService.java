package com.spongzi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spongzi.domain.User;

/**
 * @author spongzi
 * @description 针对表【user(用户表信息)】的数据库操作Service
 * @createDate 2022-11-04 12:31:48
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录接口
     *
     * @param username 用户名 / 手机号
     * @param password 密码
     * @param phone    手机号
     * @return 返回 token
     */
    String loginByPassword(String username, String password, String phone);
}
