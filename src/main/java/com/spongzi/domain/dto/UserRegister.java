package com.spongzi.domain.dto;

import lombok.Data;

/**
 * 用户注册 dto
 *
 * @author spongzi
 */
@Data
public class UserRegister {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号接收的验证码
     */
    private String code;


}
