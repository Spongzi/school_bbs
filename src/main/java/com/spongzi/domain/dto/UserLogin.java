package com.spongzi.domain.dto;

import lombok.Data;

/**
 * 用户登录注册
 *
 * @author spongzi
 */
@Data
public class UserLogin {
    /**
     * 用户登录的方式
     */
    private String type;

    /**
     * 用户的账号名称
     */
    private String username;

    /**
     * 用户的邮箱账号
     */
    private String email;

    /**
     * 账号的密码
     */
    private String password;
}
