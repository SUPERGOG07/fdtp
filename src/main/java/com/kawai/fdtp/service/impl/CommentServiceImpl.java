package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.dto.CommentDto;
import com.kawai.fdtp.mapper.AttractionMapper;
import com.kawai.fdtp.mapper.CommentMapper;
import com.kawai.fdtp.pojo.Attraction;
import com.kawai.fdtp.pojo.Comment;
import com.kawai.fdtp.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Resource
    CommentMapper commentMapper;

    @Override
    public Boolean grade(String id, int num) {
        Comment comment = commentMapper.selectById(id);
        if((comment.getGrade()+num )>=0){
            comment.setGrade(comment.getGrade()+num);
            commentMapper.updateById(comment);
            return true;
        }

        return false;
    }

    @Override
    public List<CommentDto> getCommentList(int type, String target, int level, int page, int pageSize) {

        IPage<CommentDto> pageInfo = commentMapper.getCommentList(new Page<CommentDto>(page,pageSize),type,target,level);

        return pageInfo.getRecords();
    }
}
