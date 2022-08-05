package com.kawai.fdtp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kawai.fdtp.pojo.Posts;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostsMapper extends BaseMapper<Posts> {
}
