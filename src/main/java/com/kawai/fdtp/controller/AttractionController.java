package com.kawai.fdtp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kawai.fdtp.common.HasRole;
import com.kawai.fdtp.common.R;
import com.kawai.fdtp.pojo.Attraction;
import com.kawai.fdtp.service.AttractionService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Attr;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/attraction")
@HasRole(value = {"admin"})
public class AttractionController {
    @Resource
    AttractionService attractionService;

    @PostMapping("/add")
    @ApiOperation("景点添加")
    public R<Attraction> addAttraction(Attraction attraction){
        log.info("景点添加-->attraction={}",attraction);

        attraction.setGrade(0);

        if(attractionService.save(attraction)){
            return R.success(attraction);
        }

        return R.error(Attraction.defaultConstruct());
    }

    @PutMapping("/update")
    @ApiOperation("景点更新")
    public R<Attraction> updateAttraction(Attraction attraction){
        log.info("景点更新-->attraction={}",attraction);

        if (attractionService.getById(attraction.getId())!=null){
            if (attractionService.updateById(attraction)){
                Attraction.check(attraction);
                return R.success(attraction);
            }
        }

        return R.error(Attraction.defaultConstruct());
    }

    @DeleteMapping("/delete")
    @ApiOperation("景点删除")
    public R<Attraction> deleteAttraction(String id){
        log.info("景点删除-->id={}",id);

        if (attractionService.getById(id)!=null){
            if (attractionService.removeById(id)){
                return R.success(Attraction.defaultConstruct());
            }
        }

        return R.error(Attraction.defaultConstruct());
    }

    @GetMapping("/page/address")
    @ApiOperation("根据地址推荐景点")
    @HasRole(value = {"customer"})
    public R<List<Attraction>> getAttractionByAddress(String address,Integer page,Integer size){
        log.info("根据地址推荐景点-->address={}",address);

        List<Attraction> attractions = new ArrayList<>();
        attractions.addAll(attractionService.getAttractionByAddress(address, page, size));

        if (!attractions.isEmpty()){
            return R.success(attractions);
        }

        attractions.add(Attraction.defaultConstruct());
        return R.error(attractions);
    }

}
