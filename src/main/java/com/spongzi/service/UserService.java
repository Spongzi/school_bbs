package com.spongzi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spongzi.domain.User;
import com.spongzi.domain.dto.UserRegister;

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

    /**
     * 用户注册
     *
     * @param userRegister 用户注册前端传来的信息
     * @return 返回注册结果
     */
    String register(UserRegister userRegister);

    /**
     * 发送短信
     *
     * @param phone 接收短信的手机号
     */
    void sendMsg(String phone);

    /**
     * 密码加密
     *
     * @param password 要加密的密码
     * @param salt     盐值
     * @return 返回加密后的密码
     */
    String encryptionPassword(String password, String salt);
}
