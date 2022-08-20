package com.kawai.fdtp.controller;

import com.kawai.fdtp.common.HasRole;
import com.kawai.fdtp.common.R;
import com.kawai.fdtp.pojo.Store;
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

    @GetMapping("/page")
    @ApiOperation("店铺列表")
    public R<List<Store>> getStores(String target,Integer page,Integer size){
        log.info("店铺获取-->{}",target);

        List<Store> stores = new ArrayList<>();
        stores.addAll(storeService.getStores(target, page, size));

        if (!stores.isEmpty()){
            return R.success("查询成功",stores);
        }

        stores.add(Store.defaultConstruct());
        return R.error("查询失败",stores);
    }
}
