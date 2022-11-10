package com.spongzi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spongzi.domain.Article;

/**
 * 服务条
 *
 * @author spongzi
 * @description 针对表【article(文章表)】的数据库操作Service
 * @createDate 2022-11-10 15:10:34
 * @date 2022/11/10
 */
public interface ArticleService extends IService<Article> {

    /**
     * 选择文章
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @param keyWord  关键字
     * @return 分页查询文章的结果
     */
    Page<Article> selectArticle(Integer page, Integer pageSize, String keyWord);
}
