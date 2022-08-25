package com.kawai.fdtp;

import com.kawai.fdtp.common.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@ServletComponentScan
@EnableTransactionManagement
@SpringBootApplication
@EnableAsync
public class FdtpApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(FdtpApplication.class, args);
        SpringUtil.setRoles(context);
    }

}
