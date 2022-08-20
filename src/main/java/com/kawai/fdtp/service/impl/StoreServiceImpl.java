package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.mapper.AddressMapper;
import com.kawai.fdtp.mapper.AttractionMapper;
import com.kawai.fdtp.mapper.StoreFoodMapper;
import com.kawai.fdtp.mapper.StoreMapper;
import com.kawai.fdtp.pojo.Address;
import com.kawai.fdtp.pojo.Attraction;
import com.kawai.fdtp.pojo.Store;
import com.kawai.fdtp.pojo.StoreFood;
import com.kawai.fdtp.service.StoreFoodService;
import com.kawai.fdtp.service.StoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Store> getStoresByFood(String foodName, Integer page, Integer size) {
        IPage<Store> iPage = storeMapper.getStoresByFood(new Page<>(page,size),foodName);

        return iPage.getRecords();
    }
}
