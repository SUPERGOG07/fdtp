package com.kawai.fdtp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.kawai.fdtp.pojo.Comment;

public interface CommentService extends IService<Comment> {
    //点赞和取消赞
    Boolean grade(String id,int num);
}
