package com.spongzi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spongzi.domain.User;
import com.spongzi.domain.dto.UserLoginDto;
import com.spongzi.domain.vo.UserVo;

import java.util.List;

/**
 * 管理服务
 *
 * @author spongzi
 * @date 2022/11/07
 */
public interface AdminService extends IService<User> {
    /**
     * 登录
     *
     * @param userLoginDto 用户登录dto
     * @return {@link String}
     */
    String login(UserLoginDto userLoginDto);

    /**
     * 查询用户
     *
     * @param page     页面
     * @param pagesize 页大小
     * @param username 用户名
     * @param gender   性别
     * @param status   状态
     * @return {@link Page}<{@link List}<{@link User}>>
     */
    Page<UserVo> selectUser(String page, String pagesize, String username, String gender, String status);
}
