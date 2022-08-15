package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.mapper.FansCountMapper;
import com.kawai.fdtp.pojo.FansCount;
import com.kawai.fdtp.service.FansCountService;
import org.springframework.stereotype.Service;

@Service
public class FansCountServiceImpl extends ServiceImpl<FansCountMapper, FansCount> implements FansCountService {
}
