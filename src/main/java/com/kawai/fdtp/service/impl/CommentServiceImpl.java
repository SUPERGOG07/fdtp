package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.mapper.CommentMapper;
import com.kawai.fdtp.pojo.Comment;
import com.kawai.fdtp.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
}
