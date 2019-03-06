package com.winston.userconsumer.config;


import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建 Feign客户端的配置类
 * 每个xxFeignClient如果需要用这里的配置，都需要指定该配置类
 */
@Configuration
public class FeignConfig {
    /**
     *  Feign支持四种级别
     * NONE         默认值，不记录任何日志信息
     * BASIC：      仅记录 请求的方法、URL及响应状态码和执行时间
     * HEADERS：    BASIC基础上额外记录 请求和响应的头信息
     * FULL:        记录所有请求和响应的明细（头信息、请求体、元数据）
     */
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
