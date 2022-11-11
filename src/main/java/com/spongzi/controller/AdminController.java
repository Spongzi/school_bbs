package com.spongzi.controller;

import com.spongzi.common.Result;
import com.spongzi.domain.dto.UserLoginDto;
import com.spongzi.service.AdminService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.spongzi.constant.GlobalConstant.NULL_VALUE;

/**
 * 管理控制器
 *
 * @author spongzi
 * @date 2022/11/07
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody UserLoginDto userLoginDto) {
        return Result.success(adminService.login(userLoginDto));
    }

    @PostMapping("/delete")
    public Result<Object> deleteByIds(@RequestBody List<Long> ids) {
        return Result.success(NULL_VALUE, adminService.deleteByIds(ids));
    }

    @PostMapping("/status/{status}")
    public Result<Object> modifyStatus(@PathVariable String status, @RequestBody List<Long> ids) {
        return Result.success(NULL_VALUE, adminService.modifyStatus(status, ids));
    }

}
