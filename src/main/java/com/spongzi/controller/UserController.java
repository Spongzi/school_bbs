package com.spongzi.controller;

import com.spongzi.common.Result;
import com.spongzi.domain.dto.UserLogin;
import com.spongzi.service.UserService;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/login")
    public Result<String> loginByPassword(@RequestBody UserLogin userLogin) {
        log.info("userLogin" + userLogin);
        String username = userLogin.getUsername();
        String phone = userLogin.getPhone();
        String password = userLogin.getPassword();
        return Result.success(userService.loginByPassword(username, phone, password));
    }
}
