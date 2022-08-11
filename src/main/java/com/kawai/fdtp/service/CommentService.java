package com.kawai.fdtp.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kawai.fdtp.dto.CommentDto;
import com.kawai.fdtp.pojo.Comment;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CommentService extends IService<Comment> {
    //点赞和取消赞
    Boolean grade(String id,int num);

    List<CommentDto> getCommentList(int type, String target , int level, int page, int pageSize);
}
