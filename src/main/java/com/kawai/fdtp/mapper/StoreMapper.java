package com.kawai.fdtp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kawai.fdtp.pojo.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StoreMapper extends BaseMapper<Store> {

    @Select("SELECT s.* " +
            "FROM store s , store_food f " +
            "WHERE f.normal LIKE '%${foodName}%' AND " +
            "s.id = f.target AND " +
            "s.is_check != 2 " +
            "ORDER BY s.grade DESC")
    IPage<Store> getStoresByFood(Page<Store> storePage,@Param("foodName") String foodName);
}
