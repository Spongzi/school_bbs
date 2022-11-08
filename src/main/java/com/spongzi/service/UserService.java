package com.spongzi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spongzi.domain.User;
import com.spongzi.domain.dto.UserModifyDto;
import com.spongzi.domain.dto.UserPasswordModifyDto;
import com.spongzi.domain.dto.UserRegisterDto;
import com.spongzi.domain.vo.UserVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author spongzi
 * @description 针对表【user(用户表信息)】的数据库操作Service
 * @createDate 2022-11-04 12:31:48
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录接口
     *
     * @param username 用户名 / 手机号
     * @param password 密码
     * @param email    手机号
     * @return 返回 token
     */
    String loginByPassword(String username, String email, String password);

    /**
     * 用户登录接口(使用验证码登录)
     *
     * @param email    登录的账号
     * @param password check_code
     * @return 返回token
     */
    String loginByCheckCode(String email, String password);

    /**
     * 用户注册
     *
     * @param userRegisterDto 用户注册前端传来的信息
     * @return 返回注册结果
     */
    String registerByPhoneCode(UserRegisterDto userRegisterDto);

    /**
     * 用户注册
     *
     * @param userRegisterDto 用户注册前端传来的信息
     * @return 返回注册结果
     */
    String register(UserRegisterDto userRegisterDto);

    /**
     * 发送短信
     *
     * @param phone 接收短信的手机号
     */
    void sendMsg(String phone);

    /**
     * 发送短信
     *
     * @param email 接收短信的邮箱
     */
    void sendEmailMsg(String email);

    /**
     * 密码加密
     *
     * @param password 要加密的密码
     * @param salt     盐值
     * @return 返回加密后的密码
     */
    String encryptionPassword(String password, String salt);

    /**
     * 数据去敏
     *
     * @param user 接收用户参数
     * @return 返回去敏后的数据
     */
    UserVo getSafeUser(User user);

    /**
     * 获取当前用户的信息
     *
     * @param id 要查询人的信息
     * @return 返回当前用户的信息
     */
    UserVo info(Long id);

    /**
     * 修改密码
     *
     * @param userPasswordModifyDto 用户密码修改
     * @return 返回修改结果
     */
    String modifyPassword(UserPasswordModifyDto userPasswordModifyDto);

    /**
     * 修改用户信息
     *
     *
     * @param userModifyDto 用户修改dto
     * @return {@link String}
     */
    String modifyUserInfo(UserModifyDto userModifyDto);

    /**
     * 找到用户
     *
     * @param id       id
     * @param username 用户名
     * @param email    电子邮件
     * @return {@link User}
     */
    User findUser(Long id, String username, String email);

    /**
     * 上传
     *
     * @param file 文件
     * @return 返回图片地址
     */
    String upload(MultipartFile file);

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
