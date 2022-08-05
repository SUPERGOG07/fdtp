package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.mapper.AttractionMapper;
import com.kawai.fdtp.mapper.CommentMapper;
import com.kawai.fdtp.pojo.Attraction;
import com.kawai.fdtp.pojo.Comment;
import com.kawai.fdtp.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
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
}
