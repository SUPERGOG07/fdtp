package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.mapper.UserMapper;
import com.kawai.fdtp.pojo.User;
import com.kawai.fdtp.service.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    @Cacheable(value = {"user"},key = "#root.methodName",sync = true)
    public List<User> getUser(String userName) {
        return this.baseMapper.selectList(new LambdaQueryWrapper<User>().eq(User::getUserName,userName));
    }
}
