package com.kawai.fdtp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kawai.fdtp.mapper.AttractionMapper;
import com.kawai.fdtp.pojo.Attraction;
import com.kawai.fdtp.service.AttractionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AttractionServiceImpl extends ServiceImpl<AttractionMapper, Attraction> implements AttractionService {

    @Resource
    AttractionMapper attractionMapper;

    @Override
    public Boolean grade(String id, int num) {
        Attraction attraction = attractionMapper.selectById(id);
        if((attraction.getGrade()+num )>=0){
            attraction.setGrade(attraction.getGrade()+num);
            attractionMapper.updateById(attraction);
            return true;
        }

        return false;
    }
}
