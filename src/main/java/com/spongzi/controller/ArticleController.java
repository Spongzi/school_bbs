package com.spongzi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spongzi.common.Result;
import com.spongzi.domain.Article;
import com.spongzi.domain.dto.ArticlePostDto;
import com.spongzi.exception.BlogException;
import com.spongzi.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api("文章api")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @ApiOperation("查找文章")
    @GetMapping("/search")
    public Result<Page<Article>> searchArticle(
            @ApiParam("当前页")
            @RequestParam("page") Integer page,
            @ApiParam("当前页展示的数量")
            @RequestParam("pagesize") Integer pagesize,
            @ApiParam("查询文章的关键词")
            @RequestParam(value = "keyword", required = false) String keyWord) {
        return Result.success(articleService.searchArticle(page, pagesize, keyWord));
    }

    @ApiOperation("查看文章")
    @PostMapping("/show/{articleId}")
    public Result<Article> browseArticle(
            @ApiParam("要查看的文章id")
            @PathVariable String articleId
    ) {
        return Result.success(articleService.show(articleId));
    }

    @ApiOperation("发布文章")
    @PostMapping("/post")
    public Result<String> postArticle(
            @ApiParam("文章发布的dto")
            @RequestBody ArticlePostDto articlePostDto
    ) {
        String articleId = articleService.postArticle(articlePostDto);
        if (StringUtils.isBlank(articleId)) {
            throw new BlogException(ARTICLE_POST_ERROR);
        }
        return Result.success(articleId, "发布成功");
    }
}
