package com.kawai.fdtp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kawai.fdtp.common.HasRole;
import com.kawai.fdtp.common.R;
import com.kawai.fdtp.pojo.Comment;
import com.kawai.fdtp.pojo.Food;
import com.kawai.fdtp.pojo.Store;
import com.kawai.fdtp.service.CommentService;
import com.kawai.fdtp.service.FoodService;
import com.kawai.fdtp.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
@HasRole(value = {"admin"})
public class AdminController {

    @Resource
    CommentService commentService;

    @Resource
    FoodService foodService;

    @Resource
    StoreService storeService;

    @GetMapping("/check/comment")
    public R<List<Comment>> checkComment(Integer page,Integer size){
        Page<Comment> commentPage = new Page<>(page,size);
        commentService.page(commentPage,new LambdaQueryWrapper<Comment>().eq(Comment::getIsCheck,0)
                .orderByAsc(Comment::getTime));
        List<Comment> comments = new ArrayList<>(commentPage.getRecords());

        if (!comments.isEmpty()){
            return R.success(comments);
        }

        comments.add(Comment.defaultConstruct());
        return R.error(comments);
    }

    @GetMapping("/check/food")
    public R<List<Food>> checkFood(Integer page, Integer size){
        Page<Food> foodPage = new Page<>(page,size);
        foodService.page(foodPage,new LambdaQueryWrapper<Food>().eq(Food::getIsCheck,0));
        List<Food> foods = new ArrayList<>(foodPage.getRecords());

        if (!foods.isEmpty()){
            return R.success(foods);
        }

        foods.add(Food.defaultConstruct());
        return R.error(foods);
    }

    @GetMapping("/check/store")
    public R<List<Store>> checkStore(Integer page, Integer size){
        Page<Store> storePage = new Page<>(page,size);
        storeService.page(storePage,new LambdaQueryWrapper<Store>().eq(Store::getIsCheck,0));
        List<Store> stores = new ArrayList<>(storePage.getRecords());

        if (!stores.isEmpty()){
            return R.success(stores);
        }

        stores.add(Store.defaultConstruct());
        return R.error(stores);
    }

    @PutMapping("/judge/comment")
    public R<String> judgeComment(String id,Integer judge){
        Comment comment = new Comment();
        comment.setId(id);
        comment.setIsCheck(judge);

        if (commentService.updateById(comment)){
            return R.success("");
        }

        return R.error("");
    }

    @PutMapping("/judge/food")
    public R<String> judgeFood(String id,Integer judge){
        Food food = new Food();
        food.setId(id);
        food.setIsCheck(judge);

        if (foodService.updateById(food)){
            return R.success("");
        }

        return R.error("");
    }

    @PutMapping("/judge/store")
    public R<String> judgeStore(String id,Integer judge){
        Store store = new Store();
        store.setId(id);
        store.setIsCheck(judge);

        if (storeService.updateById(store)){
            return R.success("");
        }

        return R.error("");
    }

}
