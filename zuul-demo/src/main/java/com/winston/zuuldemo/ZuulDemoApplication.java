package com.winston.zuuldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy    //开启Zuul的网关功能
@EnableDiscoveryClient  //开启eureka客户端发现功能
public class ZuulDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulDemoApplication.class, args);
    }

}
