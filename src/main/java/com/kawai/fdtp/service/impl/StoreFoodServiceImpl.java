package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.mapper.StoreFoodMapper;
import com.kawai.fdtp.pojo.StoreFood;
import com.kawai.fdtp.service.StoreFoodService;
import org.springframework.stereotype.Service;

@Service
public class StoreFoodServiceImpl extends ServiceImpl<StoreFoodMapper, StoreFood> implements StoreFoodService {
}
