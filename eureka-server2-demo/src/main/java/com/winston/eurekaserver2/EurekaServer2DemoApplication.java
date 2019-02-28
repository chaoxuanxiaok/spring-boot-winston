package com.winston.eurekaserver2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //声明这是一个EurekaServer
public class EurekaServer2DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer2DemoApplication.class, args);
    }

}
