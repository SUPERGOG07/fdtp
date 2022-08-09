package com.kawai.fdtp;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

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

}
