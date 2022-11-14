package com.spongzi.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 篇帖子dto
 *
 * @author spongzi
 * @date 2022/11/14
 */
@Data
public class ArticlePostDto implements Serializable {

    private static final long serialVersionUID = -4319584038856119380L;

    /**
     * 标题
     */
    private String title;

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
     * 分类id
     * 0 - 默认类型
     */
    private Integer cid;

    /**
     * 文章是否显示 1是 0否
     */
    private Integer isShow;

    /**
     * 是否原创
     */
    private Integer isOriginal;
}
