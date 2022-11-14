package com.spongzi.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * 记录本项目出现的异常
 *
 * @author spongzi
 */
@Getter
@Setter
@NoArgsConstructor
public class BlogException extends RuntimeException {
    /**
     * code 返回请求码
     */
    private int code;

    /**
     * msg 错误信息
     */
    private String msg;

    /**
     * desc 详细的错误信息
     */
    private String desc;

    /**
     * exceptionEnum 接收错误的枚举数据
     */
    private BlogExceptionEnum exceptionEnum;

    public BlogException(@NotNull BlogExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
        this.desc = exceptionEnum.getDesc();
    }

    public BlogException(@NotNull BlogExceptionEnum exceptionEnum, String desc) {
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
        this.desc = desc;
    }

    public BlogException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BlogException(int code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }
}
