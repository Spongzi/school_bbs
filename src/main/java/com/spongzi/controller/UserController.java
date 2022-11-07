package com.spongzi.controller;

import com.spongzi.common.Result;
import com.spongzi.domain.User;
import com.spongzi.domain.dto.UserLoginDto;
import com.spongzi.domain.dto.UserModifyDto;
import com.spongzi.domain.dto.UserPasswordModifyDto;
import com.spongzi.domain.dto.UserRegisterDto;
import com.spongzi.domain.vo.UserVo;
import com.spongzi.exception.BlogException;
import com.spongzi.interceptor.UserHolder;
import com.spongzi.service.UserService;
import lombok.extern.java.Log;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    public Result<String> loginByPassword(@RequestBody UserLoginDto userLogin) {
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
    public Result<String> registerPhoneCode(@RequestBody UserRegisterDto userRegisterDto) {
        log.info("register");
        return Result.success(userService.registerByPhoneCode(userRegisterDto));
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody UserRegisterDto userRegisterDto) {
        log.info("register");
        return Result.success(userService.registerByPhoneCode(userRegisterDto));
    }

    @GetMapping(value = {"/info/{id}", "/info"})
    public Result<UserVo> getUserInfo(@PathVariable(value = "id", required = false) Long id) {
        if (id == null) {
            id = UserHolder.getUserId();
        }
        return Result.success(userService.info(id));
    }

    /**
     * 暂时简写，后面会拆开两个部分
     *
     * @param userPasswordModifyDto 前端传来的参数
     * @return 返回结果
     */
    @PostMapping("/password")
    public Result<String> modifyPassword(@RequestBody UserPasswordModifyDto userPasswordModifyDto) {
        return Result.success(userService.modifyPassword(userPasswordModifyDto));
    }

    @PostMapping("/modify")
    public Result<String> modifyUserInfo(@RequestBody UserModifyDto userModifyDto) {
        return Result.success(userService.modifyUserInfo(userModifyDto));
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
