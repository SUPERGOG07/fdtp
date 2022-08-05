package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.mapper.PostsMapper;
import com.kawai.fdtp.pojo.Posts;
import com.kawai.fdtp.service.PostsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {

    @Resource
    PostsMapper postsMapper;

    @Transactional
    @Override
    public Boolean change(String id,int num) {
        Posts post = postsMapper.selectById(id);
        if((post.getCollect()+num )>=0){
            post.setCollect(post.getCollect()+num);
            postsMapper.updateById(post);
            return true;
        }
        return false;
    }

    @Override
    public Boolean grade(String id, int num) {
        Posts post = postsMapper.selectById(id);
        if((post.getGrade()+num)>=0){
            post.setGrade(post.getGrade()+num);
            postsMapper.updateById(post);
            return true;
        }
        return false;
    }
}


