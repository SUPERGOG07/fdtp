package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.mapper.AttractionMapper;
import com.kawai.fdtp.mapper.StoreMapper;
import com.kawai.fdtp.pojo.Attraction;
import com.kawai.fdtp.pojo.Store;
import com.kawai.fdtp.service.StoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {
    @Resource
    StoreMapper storeMapper;

    @Override
    public Boolean grade(String id, int num) {
        Store store = storeMapper.selectById(id);
        if((store.getGrade()+num )>=0){
            store.setGrade(store.getGrade()+num);
            storeMapper.updateById(store);
            return true;
        }

        return false;
    }
}
