package com.kawai.fdtp.controller;

import com.kawai.fdtp.common.HasRole;
import com.kawai.fdtp.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("store")
@HasRole({"customer"})
public class StoreController {

    @Resource
    StoreService storeService;

}
