package com.spongzi.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spongzi.domain.Article;
import com.spongzi.service.ArticleService;
import com.spongzi.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

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

    @Override
    public Page<Article> selectArticle(Integer page, Integer pageSize, String keyWord) {
        return null;
    }
}




