package com.kawai.fdtp.controller;

import com.kawai.fdtp.common.HasRole;
import com.kawai.fdtp.common.R;
import com.kawai.fdtp.service.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/grade")
@HasRole(value = {"customer"})
public class GradeController {

    @Resource
    AttractionService attractionService;
    @Resource
    CommentService commentService;
    @Resource
    FoodService foodService;
    @Resource
    PostsService postsService;
    @Resource
    StoreService storeService;

    @GetMapping("/add/{type}/{target}")
    @ApiOperation("点赞专用接口")
    public R<String> like(@PathVariable String type,@PathVariable String target){
        log.info("点赞-->{}",target);
        Boolean flag = false;
        int num = 1;
        switch (type){
            case "attraction":
                flag=attractionService.grade(target,num);
                break;
            case "comment":
                flag=commentService.grade(target,num);
                break;
            case "food":
                flag=foodService.grade(target,num);
                break;
            case "posts":
                flag=postsService.grade(target,num);
                break;
            case "store":
                flag=storeService.grade(target,num);
                break;
            default:flag=false;
        }
        if (flag){
            return R.success("点赞成功","");
        }
        return R.error("点赞失败","");
    }

    @GetMapping("/cancel/{type}/{target}")
    @ApiOperation("消赞专用接口")
    public R<String> cancel(@PathVariable String type,@PathVariable String target){
        log.info("取消赞-->{}",target);
        Boolean flag = false;
        int num = -1;
        switch (type){
            case "attraction":
                flag=attractionService.grade(target,num);
                break;
            case "comment":
                flag=commentService.grade(target,num);
                break;
            case "food":
                flag=foodService.grade(target,num);
                break;
            case "posts":
                flag=postsService.grade(target,num);
                break;
            case "store":
                flag=storeService.grade(target,num);
                break;
            default:flag=false;
        }
        if (flag){
            return R.success("取消赞成功","");
        }
        return R.error("取消赞失败","");
    }

}
