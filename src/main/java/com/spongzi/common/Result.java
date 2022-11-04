package com.spongzi.common;

import com.spongzi.exception.BlogExceptionEnum;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * 返回结果类
 *
 * @author spongzi
 */
@Data
public class Result<T> {
    public int code;
    public T data;
    public String msg;
    public String desc;

    /**
     * 成功 不带有数据
     *
     * @param <T> 泛型
     * @return 返回result结果类
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("请求成功");
        return result;
    }

    /**
     * 成功 带有数据
     *
     * @param data 数据
     * @param <T>  泛型
     * @return 返回result结果类
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("请求成功");
        result.setData(data);
        return result;
    }

    /**
     * 失败
     *
     * @param code 错误代码
     * @param msg  错误信息
     * @param <T>  泛型
     * @return 返回result结果类
     */
    public static <T> Result<T> failed(int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * 失败
     *
     * @param code 错误代码
     * @param msg  错误信息
     * @param desc 错误详细信息
     * @param <T>  泛型
     * @return 返回result结果类
     */
    public static <T> Result<T> failed(int code, String msg, String desc) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setDesc(desc);
        return result;
    }

    /**
     * 失败
     *
     * @param exceptionEnum 错误类型枚举
     * @param <T>           泛型
     * @return 返回result结果类
     */
    public static <T> Result<T> failed(BlogExceptionEnum exceptionEnum) {
        Result<T> result = new Result<>();
        result.setCode(exceptionEnum.getCode());
        result.setMsg(exceptionEnum.getMsg());
        result.setDesc(exceptionEnum.getDesc());
        return result;
    }
}
