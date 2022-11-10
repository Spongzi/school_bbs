package com.spongzi.service.impl;

import com.spongzi.domain.User;
import com.spongzi.exception.BlogException;
import com.spongzi.exception.BlogExceptionEnum;
import com.spongzi.interceptor.UserHolder;
import com.spongzi.service.CommonService;
import com.spongzi.service.UserService;
import com.spongzi.utlis.UploadImage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static com.spongzi.constant.GlobalConstant.ARTICLE_UPLOAD_IMAGE;
import static com.spongzi.constant.GlobalConstant.USER_UPLOAD_HEAD;

/**
 * 公共服务impl
 *
 * @author spongzi
 * @date 2022/11/10
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private UploadImage uploadImage;

    @Resource
    private UserService userService;

    @Override
    public String upload(MultipartFile file, String type) {
        String url = uploadImage.upload(file);
        if (USER_UPLOAD_HEAD.equals(type)) {
            Long userId = UserHolder.getUserId();
            User user = userService.getById(userId);
            if (user == null) {
                throw new BlogException(BlogExceptionEnum.USER_NOT_EXIST);
            }
            user.setAvatar(url);
            userService.updateById(user);
            return "修改头像成功";
        }
        return null;
    }
}
