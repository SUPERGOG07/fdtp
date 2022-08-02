package com.kawai.fdtp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kawai.fdtp.common.R;
import com.kawai.fdtp.pojo.Comment;
import com.kawai.fdtp.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kotlin.jvm.internal.Lambda;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @PostMapping("/add")
    @ApiOperation("添加评论")
    public R<Comment> add(@RequestBody Comment comment){
        log.info("添加评论-->{}", comment.toString());

        comment.setGrade(0);
        comment.setTime(LocalDateTime.now());
        if (commentService.save(comment)){
            return R.success(comment,"评论添加成功");
        }

        return R.error("评论添加失败");

    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除评论")
    public R<String> delete(@PathVariable Long id){
        log.info("删除评论-->{}",id);

        Comment comment = commentService.getById(id);
        if(comment!=null){
            if (commentService.removeById(id)){
                return R.success("评论删除成功");
            }
            return R.error("评论删除失败");
        }
        return R.error("评论不存在");
    }

    @PutMapping("/update")
    @ApiOperation("更新评论")
    public R<Comment> update(@RequestBody Comment comment){
        log.info("更新评论-->{}",comment.toString());

        comment.setTime(LocalDateTime.now());

        if(commentService.getById(comment.getId())!=null){
            if (commentService.updateById(comment)) {
                return R.success(comment,"评论更新成功");
            }

            return R.error("评论更新失败");
        }
        return R.error("评论不存在");
    }

    /**
     * 获取评论列表，默认排序为时间降序
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/get/page/{type}/{target}/{level}/{page}/{pageSize}")
    @ApiOperation("一级评论分页查询")
    public R<Page> page1(@PathVariable int type,@PathVariable long target ,@PathVariable int level,@PathVariable int page, @PathVariable int pageSize){
        log.info("一级评论分页查询--> type={}, page = {} , pageSize = {}",type,page,pageSize);

        Page pageInfo = new Page(page,pageSize);

        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getType,type).eq(Comment::getType,type)
                .eq(Comment::getTarget,target).eq(Comment::getLevel,level);
        wrapper.orderByDesc(Comment::getTime);

        commentService.page(pageInfo,wrapper);

        return R.success(pageInfo);
    }


}
