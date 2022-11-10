package com.spongzi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spongzi.common.Result;
import com.spongzi.domain.Article;
import com.spongzi.service.ArticleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 文章控制器
 *
 * @author spongzi
 * @date 2022/11/10
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @GetMapping("/select")
    public Result<Page<Article>> selectArticle(
            @Param("page") Integer page,
            @Param("pagesize") Integer pageSize,
            @Param("keyword") String keyWord
    ) {
        return Result.success(articleService.selectArticle(page, pageSize, keyWord));
    }
}
