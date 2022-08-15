package com.kawai.fdtp.controller;


import com.kawai.fdtp.common.HasRole;
import com.kawai.fdtp.common.R;
import com.kawai.fdtp.dto.CommentDto;
import com.kawai.fdtp.pojo.Comment;
import com.kawai.fdtp.service.CommentService;
import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comment")
@HasRole(value = {"customer"})
public class CommentController {

    @Resource
    private CommentService commentService;


    @PostMapping("/add")
    @ApiOperation("添加评论")
    public R<Comment> add(@RequestBody Comment comment){
        log.info("添加评论-->{}", comment.toString());

        comment.setGrade(0);
        comment.setTime(System.currentTimeMillis());
        if (commentService.save(comment)){
            return R.success("评论添加成功",comment);
        }

        return R.error("评论添加失败",new Comment());

    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除评论")
    public R<Comment> delete(@PathVariable String id){
        log.info("删除评论-->{}",id);

        Comment comment = commentService.getById(id);
        if(comment!=null){
            if (commentService.removeById(id)){
                return R.success("评论删除成功",comment);
            }
            return R.error("评论删除失败",comment);
        }
        return R.error("评论不存在",comment);
    }

    @PutMapping("/update")
    @ApiOperation("更新评论")
    public R<Comment> update(@RequestBody Comment comment){
        log.info("更新评论-->{}",comment.toString());

        comment.setTime(System.currentTimeMillis());

        if(commentService.getById(comment.getId())!=null){
            if (commentService.updateById(comment)) {
                return R.success("评论更新成功",comment);
            }

            return R.error("评论更新失败",comment);
        }
        return R.error("评论不存在",comment);
    }

    /**
     * 获取评论列表，默认排序为时间降序
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page/{type}/{target}/{level}/{page}/{pageSize}")
    @ApiOperation("评论分页查询")
    public R<List<CommentDto>> getPage(@PathVariable int type, @PathVariable String target , @PathVariable int level, @PathVariable int page, @PathVariable int pageSize){
        log.info("评论分页查询--> type={}, page = {} , pageSize = {}",type,page,pageSize);

        List<CommentDto> result = commentService.getCommentList(type, target, level, page, pageSize);

        if (!result.isEmpty()){
            return R.success("查询成功",result);
        }
        return R.error("未查询到评论",new ArrayList<>());

    }


}
