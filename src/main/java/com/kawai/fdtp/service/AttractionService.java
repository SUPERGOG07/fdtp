package com.kawai.fdtp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kawai.fdtp.pojo.Attraction;
import com.kawai.fdtp.pojo.Food;

import java.util.List;

public interface AttractionService extends IService<Attraction> {

    //点赞和取消赞
    Boolean grade(String id,int num);

    List<Attraction> getAttractionByAddress(String address, Integer page, Integer size);

}
