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
import java.util.Comparator;
import java.util.List;

@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {
    @Resource
    StoreMapper storeMapper;

    @Resource
    AddressMapper addressMapper;

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

    @Override
    public List<Store> getStoresByAddress(String address, Integer page, Integer size) {
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        String detail = Address.pickup(address,wrapper);

        Page<Address> storePage = new Page<>(page,size);
        addressMapper.selectPage(storePage,wrapper);
        if (storePage.getRecords().isEmpty()){
            wrapper.clear();
            wrapper.like(Address::getDetail,detail);
            addressMapper.selectPage(storePage,wrapper);
        }
        List<Address> addressList = new ArrayList<>(storePage.getRecords());

        List<Store> storeList = new ArrayList<>();

        addressList.forEach(address1 -> {
            Store store = storeMapper.selectOne(new LambdaQueryWrapper<Store>().eq(Store::getAddress,address1.getId()));
            if (store!=null){
                storeList.add(store);
            }

        });
        storeList.sort(Comparator.comparing(Store::getGrade).reversed());

        return storeList;
    }
}
