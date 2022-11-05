package com.spongzi.controller;

import com.spongzi.common.Result;
import com.spongzi.domain.dto.UserLogin;
import com.spongzi.domain.dto.UserRegister;
import com.spongzi.service.UserService;
import lombok.extern.java.Log;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @PostMapping("/loginByPassword")
    public Result<String> loginByPassword(@RequestBody UserLogin userLogin) {
        log.info("userLogin");
        String username = userLogin.getUsername();
        String phone = userLogin.getPhone();
        String password = userLogin.getPassword();
        return Result.success(userService.loginByPassword(username, phone, password));
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody UserRegister userRegister) {
        log.info("register");
        return Result.success(userService.register(userRegister));
    }

    @PostMapping("/sendMsg")
    public Result<String> getPhoneCode(@RequestBody String phone) {
        userService.sendMsg(phone);
        return Result.success("获取验证码成功");
    }
}
