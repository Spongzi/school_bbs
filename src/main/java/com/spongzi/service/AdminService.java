package com.spongzi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spongzi.domain.User;
import com.spongzi.domain.dto.UserLoginDto;

public interface AdminService extends IService<User> {
    /**
     * 登录
     *
     * @param userLoginDto 用户登录dto
     * @return {@link String}
     */
    String login(UserLoginDto userLoginDto);
}
