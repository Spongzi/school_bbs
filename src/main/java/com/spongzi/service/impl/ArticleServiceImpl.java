package com.spongzi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.spongzi.domain.Article;
import com.spongzi.service.ArticleService;
import com.spongzi.mapper.ArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.spongzi.constant.ArticleConstant.ARTICLE_REDIS_KEY;

/**
 * 文章服务impl
 *
 * @author spongzi
 * @description 针对表【article(文章表)】的数据库操作Service实现
 * @createDate 2022-11-10 15:10:34
 * @date 2022/11/10
 */
@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private final Gson gson = new Gson();

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
            Integer click;
            Article articleRedis = (Article) redisTemplate.opsForValue().get(ARTICLE_REDIS_KEY + article.getId());
            // 如果从redis中查询到的结果为空值，那么设置为数据库中的值
            // 否则就调用redis中查询到的值
            if (articleRedis == null) {
                click = article.getClick();
            } else {
                click = articleRedis.getClick();
            }
            article.setClick(click);
        });
        return articlePage;
    }

    @Override
    public Article show(String articleId) {
        // 1. 先查询redis中是否包含数据
        String articleJson = (String) redisTemplate.opsForValue().get(ARTICLE_REDIS_KEY + articleId);
        Article article = gson.fromJson(articleJson, Article.class);
        // 如果redis里存在数据返回
        // 否则去数据库中查询
        article = article == null ? articleMapper.selectById(articleId) : article;
        log.info(article.toString());
        article.setClick(article.getClick() + 1);
        String json = gson.toJson(article);
        log.info("json value is: " + json);
        redisTemplate.opsForValue().set(ARTICLE_REDIS_KEY + articleId, json);
        return article;
    }
}




