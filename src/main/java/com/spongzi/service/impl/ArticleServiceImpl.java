package com.spongzi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spongzi.domain.Article;
import com.spongzi.service.ArticleService;
import com.spongzi.mapper.ArticleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.spongzi.constant.ArticleConstant.ARTICLE_CLICK_REDIS_KEY;

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

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override

    public Page<Article> searchArticle(Integer page, Integer pagesize, String keyWord) {
        Page<Article> articlePage = new Page<>(page, pagesize);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(keyWord)) {
            queryWrapper.like(Article::getTitle, keyWord);
            queryWrapper.like(Article::getContent, keyWord);
        }
        articleMapper.selectPage(articlePage, queryWrapper);
        // 查询到的数据，进继续对点击次数进行查看
        List<Article> records = articlePage.getRecords();
        records.forEach(article -> {
            Integer click = (Integer) redisTemplate.opsForValue().get(ARTICLE_CLICK_REDIS_KEY + article.getId());
            // 如果从redis中查询到的结果为空值，那么设置为数据库中的值
            // 否则就调用redis中查询到的值
            if (click == null) {
                click = article.getClick();
            }
            article.setClick(click);
        });
        return articlePage;
    }

    @Override
    public Integer browse(String articleId) {
        // 1. 先查询redis中是否包含数据
        Integer click = (Integer) redisTemplate.opsForValue().get(ARTICLE_CLICK_REDIS_KEY + articleId);
        // 1.1 如果不存在，那么就先去数据库中查询 并且把该值存入redis
        if (click == null) {
            Article article = articleMapper.selectById(Long.valueOf(articleId));
            click = article.getClick();
            redisTemplate.opsForValue().set(ARTICLE_CLICK_REDIS_KEY + articleId, click + 1);
            return click + 1;
        }
        // 1. 2 如果存在，更新数据数据后直接返回
        redisTemplate.opsForValue().set(ARTICLE_CLICK_REDIS_KEY + articleId, click + 1);
        return click + 1;
    }
}




