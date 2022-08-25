package com.kawai.fdtp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kawai.fdtp.common.HasRole;
import com.kawai.fdtp.common.R;
import com.kawai.fdtp.pojo.Address;
import com.kawai.fdtp.pojo.Store;
import com.kawai.fdtp.pojo.StoreFood;
import com.kawai.fdtp.service.StoreFoodService;
import com.kawai.fdtp.service.StoreService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/store")
@HasRole(value = {"customer","admin"})
public class StoreController {

    @Resource
    StoreService storeService;

    @Resource
    StoreFoodService storeFoodService;

    @PostMapping("/add")
    @ApiOperation("增加店铺")
    public R<Store> addStore(Store store){
        log.info("店铺增加-->{}",store.toString());

        store.setGrade(0);
        store.setIsCheck(0);
        if(storeService.save(store)){
            Store.check(store);
            return R.success("上传成功",store);
        }
        return R.error("上传失败",Store.defaultConstruct());
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除店铺")
    public R<Store> deleteStore(@PathVariable String id){
        log.info("店铺删除-->{}",id);

        if(storeService.getById(id)!=null){
            if (storeService.removeById(id)){
                return R.success("删除成功",Store.defaultConstruct());
            }
        }
        return R.error("删除失败",Store.defaultConstruct());
    }

    @PutMapping("/update")
    @ApiOperation("更新店铺")
    public R<Store> updateStore(Store store){
        log.info("更新店铺-->{}",store.toString());

        if (storeService.getById(store.getId())!=null){
            if (storeService.updateById(store)){
                Store result = storeService.getById(store.getId());
                Store.check(result);
                return R.success("更新成功",result);
            }
        }

        return R.error("更新失败",Store.defaultConstruct());
    }

    @GetMapping("/page/food")
    @ApiOperation("根据美食搜索店铺")
    public R<List<Store>> getStoresByFood(String foodName,Integer page,Integer size){
        log.info("美食搜索店铺-->foodName={}",foodName);

        List<Store> stores = new ArrayList<>();
        stores.addAll(storeService.getStoresByFood(foodName,page,size));

        if (!stores.isEmpty()){
            Store.check(stores);
            return R.success(stores);
        }
        stores.add(Store.defaultConstruct());
        return R.error("查询失败",stores);
    }

    @GetMapping("/page/address")
    @ApiOperation("根据位置搜索店铺")
    public R<List<Store>> getStoresByAddress(String address,Integer page,Integer size){
        log.info("地址搜索店铺--> address={}",address);

        List<Store> stores = new ArrayList<>();
        stores.addAll(storeService.getStoresByAddress(address,page,size));

        if (!stores.isEmpty()){
            return R.success(stores);
        }

        stores.add(Store.defaultConstruct());
        return R.error("查询失败",stores);
    }

    @GetMapping("/page/name")
    @ApiOperation("根据名称搜索店铺")
    public R<List<Store>> getStoresByName(String name,String city){
        log.info("名字搜索店铺-->name={}",name);
        if (!city.contains("市")){
            city = city + "市";
        }

        List<Store> stores = new ArrayList<>();
        stores.addAll(storeService.getStoresByName(name, city));

        if (!stores.isEmpty()){
            return R.success(stores);
        }

        stores.add(Store.defaultConstruct());
        return R.error("查询失败",stores);
    }

    @PostMapping("/food/add")
    @ApiOperation("店铺特色菜添加")
    public R<StoreFood> addStoreFood(StoreFood storeFood){
        log.info("店铺特色菜添加-->target={}",storeFood.getTarget());

        if(storeFood.getNormal()==null || storeFood.getNormal().isEmpty()){
            storeFood.setNormal(storeFood.getName());
        }

        if(storeFoodService.getOne(new LambdaQueryWrapper<StoreFood>()
                .eq(StoreFood::getName,storeFood.getName()).eq(StoreFood::getTarget,storeFood.getTarget()))!=null){
            return R.error("已存在该特色菜",StoreFood.defaultConstruct());
        }

        if(storeFoodService.save(storeFood)){
            return R.success(storeFood);
        }
        return R.error("添加失败",StoreFood.defaultConstruct());
    }

    @PutMapping("/food/update")
    @ApiOperation("店铺特色菜更新")
    public R<StoreFood> updateStoreFood(StoreFood storeFood){
        log.info("店铺特色菜更新-->target={}",storeFood.getTarget());

        if(storeFoodService.getOne(new LambdaQueryWrapper<StoreFood>()
                .eq(StoreFood::getName,storeFood.getName()).eq(StoreFood::getTarget,storeFood.getTarget()))!=null){
            if (storeFoodService.updateById(storeFood)){
                return R.success(storeFood);
            }

            return R.error("更新失败",StoreFood.defaultConstruct());
        }
        return R.error("不存在该特色菜",StoreFood.defaultConstruct());
    }

    @DeleteMapping("/food/delete")
    @ApiOperation("店铺特色菜删除")
    public R<StoreFood> deleteStoreFood(String id){
        log.info("店铺特色菜删除--> id={}",id);

        if (storeFoodService.getById(id)!=null){
            if(storeFoodService.removeById(id)){
                return R.success(StoreFood.defaultConstruct());
            }
            return R.error("删除失败",StoreFood.defaultConstruct());
        }
        return R.error("不存在该特色菜",StoreFood.defaultConstruct());
    }

}
