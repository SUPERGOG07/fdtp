package com.kawai.fdtp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@ServletComponentScan
@EnableTransactionManagement
@SpringBootApplication
public class FdtpApplication {

    public static void main(String[] args) {
        SpringApplication.run(FdtpApplication.class, args);
    }

}
