package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.mapper.AddressMapper;
import com.kawai.fdtp.mapper.AttractionMapper;
import com.kawai.fdtp.mapper.FoodMapper;
import com.kawai.fdtp.mapper.StoreMapper;
import com.kawai.fdtp.pojo.Address;
import com.kawai.fdtp.pojo.Attraction;
import com.kawai.fdtp.pojo.Food;
import com.kawai.fdtp.pojo.Store;
import com.kawai.fdtp.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Resource
    AddressMapper addressMapper;

    @Resource
    AttractionMapper attractionMapper;

    @Resource
    FoodMapper foodMapper;

    @Resource
    StoreMapper storeMapper;

    @Override
    public Boolean addAddress(Address address) {
        boolean flag = false;
        if(addressMapper.selectOne(new LambdaQueryWrapper<Address>().eq(Address::getTarget,address.getTarget()))==null){
            if (addressMapper.insert(address)>=1){
                String addressId = address.getId();
                String target = address.getTarget();
                switch (address.getType()){
                    case 0:
                        Store store =new Store();
                        store.setId(target);
                        store.setAddress(addressId);
                        flag=(storeMapper.updateById(store)>=1);
                        break;
                    case 1:
                        Food food = new Food();
                        food.setId(target);
                        food.setAddress(addressId);
                        flag=(foodMapper.updateById(food)>=1);
                        break;
                    case 2:
                        Attraction attraction = new Attraction();
                        attraction.setId(target);
                        attraction.setAddress(addressId);
                        flag=(attractionMapper.updateById(attraction)>=1);
                        break;
                }
            }
        }
        return flag;
    }

    @Override
    public Boolean deleteAddress(String target) {
        boolean flag = false;

        Address address = addressMapper.selectOne(new LambdaQueryWrapper<Address>().eq(Address::getTarget,target));
        if (address!=null){
            Integer type = address.getType();
            switch (type){
                case 0:
                    Store store =new Store();
                    store.setId(target);
                    store.setAddress("unknown");
                    flag=(storeMapper.updateById(store)>=1);
                    break;
                case 1:
                    Food food = new Food();
                    food.setId(target);
                    food.setAddress("unknown");
                    flag=(foodMapper.updateById(food)>=1);
                    break;
                case 2:
                    Attraction attraction = new Attraction();
                    attraction.setId(target);
                    attraction.setAddress("unknown");
                    flag=(attractionMapper.updateById(attraction)>=1);
                    break;
            }
            if (addressMapper.deleteById(address.getId())<1){
                flag=false;
            }
        }
        return flag;
    }
}
