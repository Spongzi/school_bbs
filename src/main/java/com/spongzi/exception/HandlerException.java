package com.spongzi.exception;

import com.spongzi.common.Result;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理
 *
 * @author spongzi
 */
@Log
@RestControllerAdvice
public class HandlerException {
    @ExceptionHandler(value = BlogException.class)
    public Result<String> exceptionHandler(BlogException e) {
        e.printStackTrace();
        log.warning("runtime exception: " + e.getMsg());
        return Result.failed(e.getCode(), e.getMsg(), e.getDesc());
    }
}
