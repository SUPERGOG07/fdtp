package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.mapper.AddressMapper;
import com.kawai.fdtp.mapper.FoodMapper;
import com.kawai.fdtp.pojo.Address;
import com.kawai.fdtp.pojo.Food;
import com.kawai.fdtp.pojo.Store;
import com.kawai.fdtp.service.FoodService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {
    @Resource
    FoodMapper foodMapper;

    @Resource
    AddressMapper addressMapper;

    @Override
    public Boolean grade(String id, int num) {
        Food food = foodMapper.selectById(id);
        if((food.getGrade()+num )>=0){
            food.setGrade(food.getGrade()+num);
            foodMapper.updateById(food);
            return true;
        }

        return false;
    }

    @Override
    public List<Food> getFoodByAddress(String address, Integer page, Integer size) {
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getType,1);
        String detail = Address.pickup(address,wrapper);

        Page<Address> foodPage = new Page<>(page,size);
        addressMapper.selectPage(foodPage,wrapper);
        if (foodPage.getRecords().isEmpty()){
            wrapper.clear();
            wrapper.eq(Address::getType,1);
            wrapper.like(Address::getCity,detail).or().like(Address::getArea,detail).or()
                    .like(Address::getTown,detail).or().like(Address::getVillage,detail).or()
                    .like(Address::getStreet,detail).or().like(Address::getRoad,detail).or()
                    .like(Address::getDetail,detail);
            addressMapper.selectPage(foodPage,wrapper);
        }
        List<Address> addressList = new ArrayList<>(foodPage.getRecords());

        List<Food> foods = new ArrayList<>();

        addressList.forEach(address1 -> {
            Food food = foodMapper.selectOne(new LambdaQueryWrapper<Food>().eq(Food::getAddress,address1.getId()));
            if (food!=null){
                foods.add(food);
            }

        });
        foods.sort(Comparator.comparing(Food::getGrade).reversed());
        Food.check(foods);

        return foods;
    }

    @Override
    public List<Food> getFoodByName(String name, String city) {
        LambdaQueryWrapper<Food> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Food::getName,name);

        List<Food> foods = new ArrayList<>(foodMapper.selectList(wrapper));
        List<Food> result = new ArrayList<>();
        if (!foods.isEmpty()){
            foods.forEach(food -> {
                if (addressMapper.selectOne(new LambdaQueryWrapper<Address>().eq(Address::getTarget,food.getId()))
                        .getCity().equals(city)){
                    result.add(food);
                }
            });
        }

        result.sort(Comparator.comparing(Food::getGrade).reversed());
        Food.check(result);
        return result;
    }
}
