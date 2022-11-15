package com.spongzi.controller;

import com.spongzi.common.Result;
import com.spongzi.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static com.spongzi.constant.GlobalConstant.NULL_VALUE;

/**
 * 常见控制器
 *
 * @author spongzi
 * @date 2022/11/10
 */
@RestController
@RequestMapping("/common")
@Api("通用类api")
public class CommonController {

    @Resource
    private CommonService commonService;

    @ApiOperation("上传图片")
    @PostMapping("/upload")
    public Result<Object> uploadHead(
            @ApiParam("上传的文件")
            @RequestParam("file") MultipartFile file,
            @ApiParam("设置上传图片的用途  head -- 上传头像")
            @RequestParam("type") String type
    ) {
        return Result.success(NULL_VALUE, commonService.upload(file, type));
    }
}
