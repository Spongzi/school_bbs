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
    USER_NOT_EXIST(20003, "用户不存在", "用户不存在")
    ;

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
