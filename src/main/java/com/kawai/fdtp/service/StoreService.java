package com.kawai.fdtp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kawai.fdtp.pojo.Store;

import java.util.List;

public interface StoreService extends IService<Store> {

    //点赞和取消赞
    Boolean grade(String id,int num);

    //获得特定目标关联的商品列表
    List<Store> getStoresByFood(String foodName,Integer page,Integer size);
}
