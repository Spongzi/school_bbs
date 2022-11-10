package com.spongzi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spongzi.domain.Article;
import com.spongzi.service.ArticleService;
import com.spongzi.mapper.ArticleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 文章服务impl
 *
 * @author spongzi
 * @description 针对表【article(文章表)】的数据库操作Service实现
 * @createDate 2022-11-10 15:10:34
 * @date 2022/11/10
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Page<Article> searchArticle(Integer page, Integer pagesize, String keyWord) {
        Page<Article> articlePage = new Page<>(page, pagesize);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(keyWord)) {
            queryWrapper.like(Article::getTitle, keyWord);
            queryWrapper.like(Article::getContent, keyWord);
        }
        articleMapper.selectPage(articlePage, queryWrapper);
        return articlePage;
    }
}




