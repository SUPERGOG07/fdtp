package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.mapper.FoodMapper;
import com.kawai.fdtp.pojo.Food;
import com.kawai.fdtp.service.FoodService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {
    @Resource
    FoodMapper foodMapper;

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
}
