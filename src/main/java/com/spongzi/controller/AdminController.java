package com.spongzi.controller;

import com.spongzi.common.Result;
import com.spongzi.domain.dto.UserLoginDto;
import com.spongzi.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api("管理员控制器")
public class AdminController {
    @Resource
    private AdminService adminService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result<String> login(
            @ApiParam("用户登录dto") @RequestBody UserLoginDto userLoginDto
    ) {
        return Result.success(adminService.login(userLoginDto));
    }

    @ApiOperation("删除用户")
    @PostMapping("/delete")
    public Result<Object> deleteByIds(
            @ApiParam("要删除用户的ids") @RequestBody List<Long> ids
    ) {
        return Result.success(NULL_VALUE, adminService.deleteByIds(ids));
    }

    @ApiOperation("修改用户状态")
    @PostMapping("/status/{status}")
    public Result<Object> modifyStatus(
            @ApiParam("路径参数 要修改的状态 0 - 正常 1 - 用户冻结")
            @PathVariable String status,
            @ApiParam("要修改用户的ids") @RequestBody List<Long> ids
    ) {
        return Result.success(NULL_VALUE, adminService.modifyStatus(status, ids));
    }

}
