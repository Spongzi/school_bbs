package com.spongzi.controller;

import com.spongzi.common.Result;
import com.spongzi.domain.dto.UserLoginDto;
import com.spongzi.service.AdminService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 管理控制器
 *
 * @author spongzi
 * @date 2022/11/07
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody UserLoginDto userLoginDto) {
        return Result.success(adminService.login(userLoginDto));
    }
}
