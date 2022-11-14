package com.spongzi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.spongzi.domain.Article;
import com.spongzi.domain.User;
import com.spongzi.domain.dto.ArticlePostDto;
import com.spongzi.exception.BlogException;
import com.spongzi.service.ArticleService;
import com.spongzi.mapper.ArticleMapper;
import com.spongzi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.spongzi.constant.ArticleConstant.ARTICLE_REDIS_KEY;
import static com.spongzi.exception.BlogExceptionEnum.PARAM_ERROR;

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
    private UserService userService;

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

    @Override
    public String postArticle(ArticlePostDto articlePostDto) {
        if (articlePostDto == null) {
            throw new BlogException(PARAM_ERROR);
        }
        String title = articlePostDto.getTitle();
        Long authorId = articlePostDto.getAuthorId();
        String content = articlePostDto.getContent();
        String keywords = articlePostDto.getKeywords();
        String description = articlePostDto.getDescription();
        Integer cid = articlePostDto.getCid();
        Integer isShow = articlePostDto.getIsShow();
        Integer isOriginal = articlePostDto.getIsOriginal();
        if (StringUtils.isBlank(title)) {
            throw new BlogException(PARAM_ERROR, "文章标题不可以为空");
        }
        if (authorId == null) {
            throw new BlogException(PARAM_ERROR);
        }
        if (StringUtils.isBlank(content)) {
            throw new BlogException(PARAM_ERROR, "文章内容不可以为空");
        }
        if (StringUtils.isBlank(keywords)) {
            throw new BlogException(PARAM_ERROR, "文章关键词不可以为空");
        }
        if (StringUtils.isBlank(description)) {
            throw new BlogException(PARAM_ERROR, "文章描述不可以为空");
        }

        // 查出用户的用户名
        User user = userService.getById(authorId);
        String username = user.getUsername();

        // 创建文章实体类
        Article article = new Article();
        article.setTitle(title);
        article.setAuthor(username);
        article.setAuthorId(authorId);
        article.setContent(content);
        article.setKeywords(keywords);
        article.setDescription(description);
        article.setCid(cid);
        article.setIsShow(isShow);
        article.setIsOriginal(isOriginal);
        articleMapper.insert(article);
        return article.getId().toString();
    }
}




