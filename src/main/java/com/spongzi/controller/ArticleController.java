package com.spongzi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spongzi.common.Result;
import com.spongzi.domain.Article;
import com.spongzi.domain.dto.ArticlePostDto;
import com.spongzi.exception.BlogException;
import com.spongzi.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.spongzi.exception.BlogExceptionEnum.ARTICLE_POST_ERROR;

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
                                               @RequestParam(value = "keyword", required = false) String keyWord) {
        return Result.success(articleService.searchArticle(page, pagesize, keyWord));
    }

    @PostMapping("/show/{articleId}")
    public Result<Article> browseArticle(@PathVariable String articleId) {
        return Result.success(articleService.show(articleId));
    }

    @PostMapping("/post")
    public Result<String> postArticle(@RequestBody ArticlePostDto articlePostDto) {
        String articleId = articleService.postArticle(articlePostDto);
        if (StringUtils.isBlank(articleId)) {
            throw new BlogException(ARTICLE_POST_ERROR);
        }
        return Result.success(articleId, "发布成功");
    }
}
