package com.kawai.fdtp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kawai.fdtp.pojo.Posts;

public interface PostsService extends IService<Posts> {

    //收藏数增减
    Boolean change(String id,int num);

    Boolean grade(String id,int num);

}
