package com.kawai.fdtp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kawai.fdtp.dto.CommentDto;
import com.kawai.fdtp.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("SELECT c.* , u.head " +
            "FROM comment c , user u " +
            "WHERE c.user_name = u.user_name AND " +
            "c.type= #{type} AND " +
            "c.target= #{target} AND " +
            "c.level= #{level} AND " +
            "c.is_check != 2 " +
            "ORDER BY c.time")
    IPage<CommentDto> getCommentList(Page<CommentDto> pageInfo,
                                     @Param("type") int type, @Param("target") String target, @Param("level") int level);

}
