package com.spongzi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spongzi.common.Result;
import com.spongzi.domain.dto.UserLoginDto;
import com.spongzi.domain.dto.UserModifyDto;
import com.spongzi.domain.dto.UserPasswordModifyDto;
import com.spongzi.domain.dto.UserRegisterDto;
import com.spongzi.domain.vo.UserVo;
import com.spongzi.exception.BlogException;
import com.spongzi.interceptor.UserHolder;
import com.spongzi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.spongzi.constant.GlobalConstant.NULL_VALUE;
import static com.spongzi.constant.UserConstant.USER_LOGIN_BY_CHECK_CODE;
import static com.spongzi.constant.UserConstant.USER_LOGIN_BY_PASSWORD;

/**
 * 用户类 api控制器
 *
 * @author spongzi
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api("用户类api")
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation("用户登录")
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

    @ApiOperation("用户注册(手机号，暂时不适用)")
    @PostMapping("/register/future")
    public Result<String> registerPhoneCode(@RequestBody UserRegisterDto userRegisterDto) {
        log.info("register");
        return Result.success(userService.registerByPhoneCode(userRegisterDto));
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public Result<Object> register(
            @ApiParam("用户注册")
            @RequestBody UserRegisterDto userRegisterDto) {
        log.info("register");
        return Result.success(NULL_VALUE, userService.register(userRegisterDto));
    }

    @ApiOperation("查看用户详细信息")
    @GetMapping(value = {"/info/{id}", "/info"})
    public Result<UserVo> getUserInfo(
            @ApiParam("用户id")
            @PathVariable(value = "id", required = false) Long id) {
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
    @ApiOperation("修改密码")
    @PostMapping("/password")
    public Result<Object> modifyPassword(
            @ApiParam("用户修改密码的dto")
            @RequestBody UserPasswordModifyDto userPasswordModifyDto) {
        return Result.success(NULL_VALUE, userService.modifyPassword(userPasswordModifyDto));
    }

    @ApiOperation("修改")
    @PostMapping("/modify")
    public Result<Object> modifyUserInfo(
            @ApiParam("用户修改实体类")
            @RequestBody UserModifyDto userModifyDto) {
        return Result.success(NULL_VALUE, userService.modifyUserInfo(userModifyDto));
    }

    @ApiOperation("查询用户(管理员也调用该接口查询所有用户)")
    @GetMapping("/select")
    public Result<Page<UserVo>> selectUser(
            @ApiParam("性别")
            @RequestParam(value = "gender", required = false) String gender,
            @ApiParam("当前页")
            @RequestParam(value = "page", required = false) String page,
            @ApiParam("当前页展示数量")
            @RequestParam(value = "pagesize", required = false) String pagesize,
            @ApiParam("用户状态 0 - 正常 1 - 封禁")
            @RequestParam(value = "status", required = false) String status,
            @ApiParam("用户名称")
            @RequestParam(value = "username", required = false) String username
    ) {
        log.info(page);
        return Result.success(userService.selectUser(page, pagesize, username, gender, status));
    }

    @ApiOperation("发送手机验证码(暂时不用)")
    @PostMapping("/sendMsg")
    public Result<Object> getPhoneCode(@Param("phone") String phone) {
        userService.sendMsg(phone);
        return Result.success(NULL_VALUE, "获取验证码成功");
    }

    @ApiOperation("发送邮箱")
    @PostMapping("/sendEmail")
    public Result<Object> getEmailCode(
            @ApiParam("接收code的邮箱") @Param("email") String email
    ) {
        log.info(email);
        userService.sendEmailMsg(email);
        return Result.success(NULL_VALUE, "获取验证码成功");
    }

    @ApiOperation("用户登出")
    @GetMapping("/logout")
    public Result<Object> logout() {
        return Result.success(NULL_VALUE, "退出登录成功");
    }
}
