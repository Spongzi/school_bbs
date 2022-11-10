package com.spongzi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 文章表
 * @TableName article
 */
@TableName(value ="article")
@Data
public class Article implements Serializable {
    /**
     * 文章id
     */
    @TableId
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 作者id
     */
    private Long authorId;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 描述
     */
    private String description;

    /**
     * 点击数
     */
    private Integer click;

    /**
     * 分类id
     */
    private Integer cid;

    /**
     * 文章是否显示 1是 0否
     */
    private Integer isShow;

    /**
     * 是否置顶 1是 0否
     */
    private Integer isTop;

    /**
     * 是否原创
     */
    private Integer isOriginal;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}