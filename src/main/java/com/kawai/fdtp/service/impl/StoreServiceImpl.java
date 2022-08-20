package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.mapper.AttractionMapper;
import com.kawai.fdtp.mapper.StoreMapper;
import com.kawai.fdtp.pojo.Attraction;
import com.kawai.fdtp.pojo.Store;
import com.kawai.fdtp.service.StoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public List<Store> getStores(String target,Integer page,Integer size) {
        LambdaQueryWrapper<Store> wrapper = new LambdaQueryWrapper<>();
        if(!target.equals("all")){
            wrapper.eq(Store::getTarget,target);
        }
        wrapper.eq(Store::getIsCheck,1)
                .orderByDesc(Store::getGrade);

        Page<Store> pageInfo = new Page<>(page,size);
        storeMapper.selectPage(pageInfo,wrapper);

        return pageInfo.getRecords();
    }
}
