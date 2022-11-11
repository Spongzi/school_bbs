package com.spongzi.exception;

import lombok.Getter;

/**
 * 博客错误枚举类型
 *
 * @author spongzi
 */
@Getter
public enum BlogExceptionEnum {
    /**
     * 成功类型 200
     */
    SUCCESS(200, "成功", "成功"),

    /**
     * 参数错误
     */
    PARAM_ERROR(20001, "参数错误", "前端发送的参数错误"),

    /**
     * 用户信息错误
     */
    USER_PASSWORD_ERROR(20002, "用户密码不匹配", "用户密码不匹配"),
    USER_NOT_EXIST(20003, "用户不存在", "用户不存在"),
    USER_EMAIL_CODE_ERROR(20004, "用户验证码错误", "用户手机号验证码错误"),
    USER_EXIST_ERROR(20005, "用户名已存在", "用户名已存在"),
    USER_FREEZE(20006, "用户被冻结", "用户被冻结"),
    USER_NOT_LOGIN(20007, "用户没有登录", "用户没有登陆请登录"),
    USER_OLD_PASSWORD_ERROR(20008, "用户旧密码错误", "用户旧密码错误"),
    USER_OLD_PASSWORD_SAME(20009, "用户新密码与就旧密码相同", "用户新密码与旧密码相同"),
    USER_GET_CODE_ERROR(20010, "用户获取验证码失败", "用户获取验证码失败，请重新获取"),
    USER_PERMISSION_ERROR(20011, "用户权限错误", "用户权限不足请联系管理员"),
    USER_EMAIL_ERROR(20012, "用户输入邮箱错误", "请检查用户输入的邮箱"),
    USER_UPLOAD_FILE_ERROR(20013, "用户上传的文件错误", "用户上传的文件出现错误"),
    USER_EMAIL_RE_ERROR(20014, "用户邮箱已被注册", "用户邮箱已经被注册请更换其他邮箱");

    /**
     * code 返回请求码
     */
    private final int code;

    /**
     * msg 错误信息
     */
    private final String msg;

    /**
     * desc 详细的错误信息
     */
    private final String desc;

    BlogExceptionEnum(int code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }
}
