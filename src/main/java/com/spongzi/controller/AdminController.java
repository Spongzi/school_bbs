package com.spongzi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spongzi.common.Result;
import com.spongzi.domain.User;
import com.spongzi.domain.dto.UserLoginDto;
import com.spongzi.domain.vo.UserVo;
import com.spongzi.service.AdminService;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理控制器
 *
 * @author spongzi
 * @date 2022/11/07
 */
@Log
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody UserLoginDto userLoginDto) {
        return Result.success(adminService.login(userLoginDto));
    }

    @GetMapping("/selectUser")
    public Result<Page<UserVo>> selectUser(@RequestParam(value = "gender", required = false) String gender,
                                           @RequestParam(value = "page", required = false) String page,
                                           @RequestParam(value = "pagesize", required = false) String pagesize,
                                           @RequestParam(value = "status", required = false) String status,
                                           @RequestParam(value = "username", required = false) String username) {
        log.info(page);
        return Result.success(adminService.selectUser(page, pagesize, username, gender, status));
    }
}
