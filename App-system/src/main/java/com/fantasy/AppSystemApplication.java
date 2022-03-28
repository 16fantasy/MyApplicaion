package com.fantasy;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author jingc
 * @description AppSystemApplication
 * @since 2022/2/21
 */

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class AppSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppSystemApplication.class,args);
    }
}
