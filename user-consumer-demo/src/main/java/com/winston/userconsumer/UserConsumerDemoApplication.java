package com.winston.userconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient      //开启客户端功能
@EnableHystrix          //开启Hystrix熔断机制
@EnableFeignClients     //开启Feign调用功能，此时可以删除RestTemplate的注册，因为Feign已经自动集成Ribbon的负载均衡
public class UserConsumerDemoApplication {

    //在启动类中注入RestTemplate类
    @Bean
    @LoadBalanced   //加载Eureka的组件Ribbon功能
    public RestTemplate restTemplate(){
        //这次是使用OKHttp客户端，只需要注入工厂类就可以
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }

    public static void main(String[] args) {
        SpringApplication.run(UserConsumerDemoApplication.class, args);
    }

}
