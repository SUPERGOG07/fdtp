package com.kawai.fdtp.controller;

import com.kawai.fdtp.common.HasRole;
import com.kawai.fdtp.service.CommentService;
import com.kawai.fdtp.service.FoodService;
import com.kawai.fdtp.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

}
