package com.spongzi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spongzi.common.Result;
import com.spongzi.domain.User;
import com.spongzi.domain.dto.UserLoginDto;
import com.spongzi.domain.vo.UserVo;
import com.spongzi.service.AdminService;
import com.spongzi.service.UserService;
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

    @PostMapping("/delete")
    public Result<String> deleteByIds(@RequestBody List<Long> ids) {
        return Result.success(adminService.deleteByIds(ids));
    }

    /**
     * TODO 没有测试
     *
     * @param status 状态码
     * @return 返回结果
     */
    @PostMapping("/status/{status}")
    public Result<String> modifyStatus(@PathVariable String status, @RequestBody List<Long> ids) {
        return Result.success(adminService.modifyStatus(status, ids));
    }

}
