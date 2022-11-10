package com.spongzi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spongzi.domain.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章映射器
 *
 * @author spongzi
 * @description 针对表【article(文章表)】的数据库操作Mapper
 * @createDate 2022-11-10 15:10:34
 * @Entity com.spongzi.domain.Article
 * @date 2022/11/10
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}




