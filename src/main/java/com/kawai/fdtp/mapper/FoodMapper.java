package com.kawai.fdtp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kawai.fdtp.pojo.Food;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoodMapper extends BaseMapper<Food> {
}
