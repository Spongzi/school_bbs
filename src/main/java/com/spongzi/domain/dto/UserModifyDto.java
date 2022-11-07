package com.spongzi.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户修改dto
 *
 * @author spongzi
 * @date 2022/11/07
 */
@Data
public class UserModifyDto implements Serializable {

    private static final long serialVersionUID = 7688645466631865176L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 性别
     */
    private String gender;

    /**
     * 地址
     */
    private String address;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 年龄
     */
    private String age;

    /**
     * 头像
     */
    private String avatar;
}
