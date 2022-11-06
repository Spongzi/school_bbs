package com.spongzi.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 展示的User信息 (去敏后的数据)
 *
 * @author spongzi
 */
@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = 4204266289075040657L;

    /**
     * 主键
     */
    private Long id;

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
     * 邮箱
     */
    private String email;

    /**
     * 角色
     */
    private String role;

    /**
     * 年龄
     */
    private String age;

    /**
     * 状态
     */
    private String userStatus;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    private Integer isDeleted;

}
