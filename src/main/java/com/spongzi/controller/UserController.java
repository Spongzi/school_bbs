package com.spongzi.controller;

import com.spongzi.common.Result;
import com.spongzi.domain.dto.UserLogin;
import com.spongzi.domain.dto.UserRegister;
import com.spongzi.exception.BlogException;
import com.spongzi.service.UserService;
import lombok.extern.java.Log;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Map;

import static com.spongzi.constant.UserConstant.USER_LOGIN_BY_CHECK_CODE;
import static com.spongzi.constant.UserConstant.USER_LOGIN_BY_PASSWORD;

/**
 * 用户类 api控制器
 *
 * @author spongzi
 */
@Log
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<String> loginByPassword(@RequestBody UserLogin userLogin) {
        log.info("userLogin");
        String type = userLogin.getType();
        String username = userLogin.getUsername();
        String email = userLogin.getEmail();
        String password = userLogin.getPassword();
        if (USER_LOGIN_BY_PASSWORD.equals(type)) {
            return Result.success(userService.loginByPassword(username, email, password));
        }
        if (USER_LOGIN_BY_CHECK_CODE.equals(type)) {
            return Result.success(userService.loginByCheckCode(email, password));
        }
        throw new BlogException();
    }

    @PostMapping("/register/future")
    public Result<String> registerPhoneCode(@RequestBody UserRegister userRegister) {
        log.info("register");
        return Result.success(userService.registerByPhoneCode(userRegister));
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody UserRegister userRegister) {
        log.info("register");
        return Result.success(userService.registerByPhoneCode(userRegister));
    }

    @PostMapping("/sendMsg")
    public Result<String> getPhoneCode(@Param("phone") String phone) {
        userService.sendMsg(phone);
        return Result.success("获取验证码成功");
    }

    @PostMapping("/sendEmail")
    public Result<String> getEmailCode(@Param("email") String email) {
        log.info(email);
        userService.sendEmailMsg(email);
        return Result.success("获取验证码成功");
    }
}
