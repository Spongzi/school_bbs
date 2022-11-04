package com.spongzi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spongzi.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author spongzi
 * @description 针对表【user(用户表信息)】的数据库操作Mapper
 * @createDate 2022-11-04 12:31:48
 * @Entity generator.domain.User
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




