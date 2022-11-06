package com.spongzi.domain.dto;

import lombok.Data;

/**
 * 用户密码修改
 *
 * @author spongzi
 * @date 2022/11/06
 */
@Data
public class UserPasswordModify {
    /**
     * 类型
     * 忘记密码 forget, 修改密码 modify
     */
    private String type;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 代码
     */
    private String code;
}
