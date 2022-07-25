package com.kawai.fdtp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Slf4j
@ServletComponentScan
@EnableTransactionManagement
public class FdtpApplication {

    public static void main(String[] args) {
        SpringApplication.run(FdtpApplication.class, args);
    }

}
