package com.kawai.fdtp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kawai.fdtp.common.R;
import com.kawai.fdtp.pojo.Collect;
import com.kawai.fdtp.service.CollectService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Resource
    CollectService collectService;

    @PostMapping("/add")
    @ApiOperation("添加收藏")
    public R<String> add(@RequestBody Collect collect){
        log.info("收藏增加-->{}",collect.toString());

        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getUserName,collect.getUserName()).eq(Collect::getType,collect.getType())
                .eq(Collect::getTarget,collect.getTarget());
        if(collectService.getOne(wrapper)!=null){
            return R.error("已存在该收藏");
        }else {
            collect.setTime(System.currentTimeMillis());
            if(collectService.save(collect)){
                return R.success("对象收藏成功");
            }else return R.error("对象收藏失败");
        }
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除收藏")
    public R<String> delete(@PathVariable Long id){
        log.info("收藏删除-->{}",id);

        if(collectService.getById(id)!=null){
            if(collectService.removeById(id)){
                return R.success("收藏删除成功");
            }
            return R.error("收藏删除失败");
        }

        return R.error("该收藏不存在");
    }

    @PutMapping("/update")
    @ApiOperation("更新收藏")
    public R<Collect> update(@RequestBody Collect collect){
        log.info("收藏更新-->{}",collect.toString());

        if(collectService.getById(collect.getId())!=null){
            collect.setTime(System.currentTimeMillis());
            if(collectService.updateById(collect)){
                return R.success(collect,"收藏更新成功");
            }

            return R.error("收藏更新失败");
        }
        return R.error("该收藏不存在");
    }

    @GetMapping("/page/{userName}/{type}/{page}/{pageSize}")
    @ApiOperation("获取多个收藏")
    public R<Page> page(@PathVariable String userName,@PathVariable Integer type,@PathVariable Integer page,@PathVariable Integer pageSize){
        log.info("收藏获取--> username={} , type={}",userName,type);

        Page pageInfo = new Page(page,pageSize);
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getUserName,userName).eq(Collect::getType,type).orderByDesc(Collect::getTime);

        collectService.page(pageInfo,wrapper);

        if(pageInfo.getTotal()!=0){
            return R.success(pageInfo);
        }

        return R.error("查询失败");

    }

}
