package com.wisnton.eurekaserver1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServer1DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer1DemoApplication.class, args);
    }

}
