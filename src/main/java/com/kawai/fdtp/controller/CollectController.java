package com.kawai.fdtp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kawai.fdtp.common.HasRole;
import com.kawai.fdtp.common.R;
import com.kawai.fdtp.pojo.Collect;
import com.kawai.fdtp.service.CollectService;
import com.kawai.fdtp.service.PostsService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/collect")
@HasRole(value = {"customer","admin"})
public class CollectController {

    @Resource
    CollectService collectService;
    @Resource
    PostsService postsService;

    @PostMapping("/add")
    @ApiOperation("添加收藏")
    public R<Collect> add(Collect collect){
        log.info("收藏增加-->{}",collect.toString());

        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getUserName,collect.getUserName()).eq(Collect::getType,collect.getType())
                .eq(Collect::getTarget,collect.getTarget());
        if(collectService.getOne(wrapper)!=null){
            return R.error("已存在该收藏",Collect.defaultConstruct());
        }else {
            collect.setTime(System.currentTimeMillis());
            if(collectService.save(collect)){
                if (collect.getType()==3){
                    postsService.change(collect.getTarget(),1);
                }
                return R.success("对象收藏成功",collect);
            }else return R.error("对象收藏失败",Collect.defaultConstruct());
        }
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除收藏")
    public R<Collect> delete(@PathVariable String id){
        log.info("收藏删除-->{}",id);

        Collect collect = collectService.getById(id);

        if(collect!=null){
            if(collectService.removeById(id)){
                if (collect.getType()==3){
                    postsService.change(collect.getTarget(),-1);
                }

                return R.success("收藏删除成功",collect);
            }
            return R.error("收藏删除失败",Collect.defaultConstruct());
        }

        return R.error("该收藏不存在",Collect.defaultConstruct());
    }

    @PutMapping("/update")
    @ApiOperation("更新收藏")
    public R<Collect> update( Collect collect){
        log.info("收藏更新-->{}",collect.toString());

        if(collectService.getById(collect.getId())!=null){
            collect.setTime(System.currentTimeMillis());
            if(collectService.updateById(collect)){
                return R.success("收藏更新成功",collect);
            }

            return R.error("收藏更新失败",Collect.defaultConstruct());
        }
        return R.error("该收藏不存在",Collect.defaultConstruct());
    }

    @GetMapping("/page/{userName}/{type}/{page}/{pageSize}")
    @ApiOperation("获取多个收藏")
    public R<List<Collect>> page(@PathVariable String userName, @PathVariable Integer type, @PathVariable Integer page, @PathVariable Integer pageSize){
        log.info("收藏获取--> username={} , type={}",userName,type);

        Page pageInfo = new Page(page,pageSize);
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getUserName,userName).eq(Collect::getType,type).orderByDesc(Collect::getTime);

        collectService.page(pageInfo,wrapper);

        if(pageInfo.getTotal()!=0){

            return R.success("查询成功",pageInfo.getRecords());
        }

        List<Collect> list = new ArrayList<>();
        list.add(Collect.defaultConstruct());
        return R.error("查询失败",list);

    }

    @GetMapping("/count/{target}")
    @ApiOperation("获得目标被收藏数")
    public R<Long> count(@PathVariable String target){
        log.info("收藏数量获取--> target={}",target);

        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getTarget,target);

        Long count = collectService.count(wrapper);

        return R.success("统计成功",count);
    }

}
