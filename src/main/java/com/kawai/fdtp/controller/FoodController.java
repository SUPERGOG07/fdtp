package com.kawai.fdtp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kawai.fdtp.common.HasRole;
import com.kawai.fdtp.common.R;
import com.kawai.fdtp.pojo.Food;
import com.kawai.fdtp.pojo.Store;
import com.kawai.fdtp.service.FoodService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/food")
@HasRole(value = {"customer","admin"})
public class FoodController {

    @Resource
    FoodService foodService;

    @PostMapping("/add")
    @ApiOperation("添加美食")
    public R<Food> addFood(Food food){
        log.info("添加美食-->food={}",food.getName());

        Food.check(food);
        if (foodService.getOne(new LambdaQueryWrapper<Food>().eq(Food::getName,food.getName()))!=null){
            return R.error("已存在该食物",Food.defaultConstruct());
        }

        if (foodService.save(food)){
            return R.success(food);
        }

        return R.error("上传失败",Food.defaultConstruct());
    }

    @PutMapping("/update")
    @ApiOperation("更新美食")
    public R<Food> updateFood(Food food){
        log.info("更新美食-->food={}",food.getName());

        if (foodService.getOne(new LambdaQueryWrapper<Food>().eq(Food::getId,food.getId()))!=null){
            if (foodService.updateById(food)){
                Food result = foodService.getById(food.getId());
                Food.check(result);
                return R.success(result);
            }
        }
        return R.error("更新失败",Food.defaultConstruct());
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除美食")
    public R<Food> deleteFood(String id){
        log.info("删除美食-->food={}",id);

        if (foodService.getById(id)!=null){
            if (foodService.removeById(id)){
                return R.success(Food.defaultConstruct());
            }
        }
        return R.error("删除失败",Food.defaultConstruct());
    }

    @GetMapping("/page/address")
    @ApiOperation("根据地址推荐美食")
    public R<List<Food>> getFoodByAddress(String address,Integer page,Integer size) {
        log.info("根据地址推荐美食-->address={}", address);

        List<Food> foods = new ArrayList<>();
        foods.addAll(foodService.getFoodByAddress(address, page, size));

        if (!foods.isEmpty()){
            return R.success(foods);
        }

        foods.add(Food.defaultConstruct());
        return R.error(foods);
    }

    @GetMapping("/page/name")
    @ApiOperation("根据名称推荐美食")
    public R<List<Food>> getFoodByName(String name,String city){
        log.info("根据名称推荐美食-->name={}",name);

        List<Food> foods = new ArrayList<>();
        foods.addAll(foodService.getFoodByName(name, city));

        if (!foods.isEmpty()){
            return R.success(foods);
        }

        foods.add(Food.defaultConstruct());
        return R.error(foods);
    }

    @GetMapping("/page/grade")
    @ApiOperation("根据评分推荐美食")
    public R<List<Food>> getFoodByGrade(Integer page,Integer size){
        Page<Food> foodPage = new Page<>(page,size);
        foodService.page(foodPage,new LambdaQueryWrapper<Food>().orderByAsc(Food::getGrade));

        List<Food> foods = new ArrayList<>(foodPage.getRecords());

        if (!foods.isEmpty()){
            return R.success(foodPage.getRecords());
        }

        foods.add(Food.defaultConstruct());
        return R.error(foods);
    }
}
