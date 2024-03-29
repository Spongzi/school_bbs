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
    String USER_NORMAL = "0";

    /**
     * 用户被冻结
     */
    String USER_FREEZE = "1";

    /**
     * 用户使用密码登录
     */
    String USER_LOGIN_BY_PASSWORD = "password";

    /**
     * 用户使用验证码登陆
     */
    String USER_LOGIN_BY_CHECK_CODE = "code";

    /**
     * 用户修改密码 原因：忘记密码
     */
    String USER_MODIFY_PASSWORD_FORGET = "forget";

    /**
     * 用户修改密码 原因：修改密码
     */
    String USER_MODIFY_PASSWORD_MODIFY = "modify";

    /**
     * 用户管理
     */
    String USER_ADMIN = "1";

    /**
     * 令牌 存入redis前缀
     */
    String TOKEN_REDIS = "token";

    /**
     * 令牌过期
     */
    int TOKEN_EXPIRED = 7;
}
