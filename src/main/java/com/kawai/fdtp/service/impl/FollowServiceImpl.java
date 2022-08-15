package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.mapper.FollowMapper;
import com.kawai.fdtp.pojo.Follow;
import com.kawai.fdtp.service.FollowService;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {
}
