package com.spongzi.common;

import com.google.gson.Gson;
import com.spongzi.domain.Article;
import com.spongzi.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.spongzi.constant.ArticleConstant.ARTICLE_REDIS_KEY;

/**
 * cron调度工作
 *
 * @author spongzi
 * @date 2022/11/11
 */
@Slf4j
@Component
public class CronSchedulerJob {

    private final Gson gson = new Gson();

    @Resource
    private ArticleService articleService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 更新数据库数据
     * 每天凌晨一点更新
     */
    @Scheduled(cron = "0 0 1 * * ? ")
    private void updateDbData() {
        log.info("Start synchronizing database data~~~");
        // 查询出所有的文章数据
        List<Article> list = articleService.list();
        // 对所有文章进行操作
        List<Article> articleList = list.stream().map(article -> {
            Article newArticle;
            // 查找redis中是否存在
            String json = (String) redisTemplate.opsForValue().get(ARTICLE_REDIS_KEY + article.getId());
            if (StringUtils.isBlank(json)) {
                return article;
            }
            newArticle = gson.fromJson(json, Article.class);
            return newArticle;
        }).collect(Collectors.toList());
        // 对文章进行统一更新
        articleService.updateBatchById(articleList);
        log.info("modify database data finish ~~~");
    }
}
