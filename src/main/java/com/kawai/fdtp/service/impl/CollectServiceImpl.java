package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.mapper.CollectMapper;
import com.kawai.fdtp.pojo.Collect;
import com.kawai.fdtp.service.CollectService;
import org.springframework.stereotype.Service;

@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper,Collect> implements CollectService {
}
