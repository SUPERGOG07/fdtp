package com.kawai.fdtp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kawai.fdtp.common.HasRole;
import com.kawai.fdtp.common.R;
import com.kawai.fdtp.pojo.Address;
import com.kawai.fdtp.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/address")
@HasRole(value = {"customer","admin"})
public class AddressController {

    @Resource
    AddressService addressService;

    @PostMapping("/add")
    @ApiOperation("添加地址")
    public R<Address> addAddress(Address address){
        log.info("地址添加--> target={}",address.getTarget());
        Address.check(address);

        if(addressService.getOne(new LambdaQueryWrapper<Address>().eq(Address::getTarget,address.getTarget()))!=null){
            return R.error("已经存在该地址",Address.defaultConstruct());
        }

        if (addressService.addAddress(address)) {
            return R.success(address);
        }

        return R.error("保存地址失败",Address.defaultConstruct());
    }

    @PutMapping("/update")
    @ApiOperation("更新地址")
    public R<Address> updateAddress(Address address){
        log.info("地址更新--> target={}",address.getTarget());

        if (addressService.getOne(new LambdaQueryWrapper<Address>().eq(Address::getTarget,address.getTarget()))!=null){
            if(addressService.updateById(address)){
                return R.success(address);
            }
            return R.error("更新失败",Address.defaultConstruct());
        }
        return R.error("不存在该地址",Address.defaultConstruct());
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除地址")
    public R<Address> deleteAddress(String target){
        log.info("地址删除--> target={}",target);

        if (addressService.deleteAddress(target)){
            return R.success(Address.defaultConstruct());
        }
        return R.error("删除失败",Address.defaultConstruct());
    }
}
