package com.kawai.fdtp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kawai.fdtp.pojo.Food;

public interface FoodService extends IService<Food> {
    //点赞和取消赞
    Boolean grade(String id,int num);
}
