package com.spongzi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spongzi.common.Result;
import com.spongzi.domain.Article;
import com.spongzi.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/search")
    public Result<Page<Article>> searchArticle(@RequestParam("page") Integer page,
                                                 @RequestParam("pagesize") Integer pagesize,
                                                 @RequestParam(value = "keword", required = false) String keyWord) {
        return Result.success(articleService.searchArticle(page, pagesize, keyWord));
    }
}
