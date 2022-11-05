package com.spongzi.constant;

/**
 * 用户相关所需要的常量
 *
 * @author spongzi
 */
public interface UserConstant {
    /**
     * 最短密码长度
     */
    int PASSWORD_SHORT_LENGTH = 8;

    /**
     * 最短用户名长度
     */
    int USERNAME_SHORT_LENGTH = 4;

    /**
     * 手机号长度
     */
    int PHONE_LENGTH = 11;

    /**
     * 手机验证码长度
     */
    int CHECK_CODE_LENGTH = 6;

    /**
     * 存入redis的前缀
     */
    String CHECK_CODE = "CHECK_CODE";

    /**
     * 用户正常状态
     */
    String USER_NORMAL = "1";
}
