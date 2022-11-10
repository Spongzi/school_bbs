package com.spongzi.controller;

import com.spongzi.common.Result;
import com.spongzi.service.CommonService;
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
public class CommonController {

    @Resource
    private CommonService commonService;

    @PostMapping("/upload")
    public Result<Object> uploadHead(@RequestParam("file") MultipartFile file, @RequestParam("type") String type) {
        return Result.success(NULL_VALUE, commonService.upload(file, type));
    }
}
