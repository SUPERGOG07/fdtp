package com.kawai.fdtp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kawai.fdtp.pojo.Comment;
import com.kawai.fdtp.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class FdtpApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired private StringRedisTemplate redisTemplate;
    @Test
    public void testSelect() {
        System.out.println(redisTemplate);
    }

    @Resource
    CommentService commentService;


}
