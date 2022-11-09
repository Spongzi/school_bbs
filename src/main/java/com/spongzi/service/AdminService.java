package com.spongzi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spongzi.domain.User;
import com.spongzi.domain.dto.UserLoginDto;

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
     * 删除 通过 ids
     *
     * @param ids id
     * @return {@link String}
     */
    String deleteByIds(List<Long> ids);

    /**
     * 修改状态
     *
     * @param status 状态
     * @param ids    id
     * @return {@link String}
     */
    String modifyStatus(String status, List<Long> ids);
}
