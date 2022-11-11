package com.spongzi.service.impl;

import com.spongzi.domain.User;
import com.spongzi.exception.BlogException;
import com.spongzi.exception.BlogExceptionEnum;
import com.spongzi.interceptor.UserHolder;
import com.spongzi.service.CommonService;
import com.spongzi.service.UserService;
import com.spongzi.utlis.UploadImage;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.spongzi.constant.GlobalConstant.*;

/**
 * 公共服务impl
 *
 * @author spongzi
 * @date 2022/11/10
 */
@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private UploadImage uploadImage;

    @Resource
    private UserService userService;

    @Override
    public String upload(MultipartFile file, String type) {
        log.info("type value" + type);
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)) {
            throw new BlogException(BlogExceptionEnum.USER_UPLOAD_FILE_ERROR);
        }
        int lastIndexOf = originalFilename.lastIndexOf(".");
        //获取文件后缀
        String suffix = originalFilename.substring(lastIndexOf - 1);
        //使用UUID随机产生文件名称，防止同名文件覆盖
        String fileName = UUID.randomUUID() + suffix;
        // 用日期进行对图片进行分割的管理
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        fileName = simpleDateFormat.format(new Date()) + "/" + fileName;
        // 如果是用户上传头像
        if (USER_UPLOAD_HEAD.equals(type)) {
            String key = USER_UPLOAD_HEAD_KEY + fileName;
            String url = uploadImage.upload(file, key);
            log.info("image url is: " + url);
            Long userId = UserHolder.getUserId();
            User user = userService.getById(userId);
            if (user == null) {
                throw new BlogException(BlogExceptionEnum.USER_NOT_EXIST);
            }
            user.setAvatar(url);
            userService.updateById(user);
            return "修改头像成功";
        }
        return "上传图片成功";
    }
}
