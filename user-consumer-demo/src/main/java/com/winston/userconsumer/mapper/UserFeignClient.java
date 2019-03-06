package com.winston.userconsumer.mapper;

import com.winston.userconsumer.config.FeignConfig;
import com.winston.userconsumer.mapper.impl.UserFeignClientFallback;
import com.winston.userconsumer.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 第五阶段：通过feign去调用服务
 *  对应服务所有暴露的api如果需要调用 都可以直接在这个类里加方法
 *
 *  value：指定要调用的服务名称
 *  fallback：指定实现类，当服务调用超时，服务降级找fallback函数  需要用hystrix功能才传这个参数
 *  configuration：指定配置类
 */
//@FeignClient("user-service-demo")   //声明这是一个Feign客户端，伪装成controller，指定要调用的服务id
//@FeignClient(value="user-service-demo",fallback = UserFeignClientFallback.class)
@FeignClient(value = "user-service-demo",fallback = UserFeignClientFallback.class,configuration = FeignConfig.class)
public interface UserFeignClient {  //这是个接口，Feign通过动态代理，帮我们生成实现类
    //Feign会根据注解帮我们生成URL，并访问获取结果
    @GetMapping("/user/{id}")
    User queryUserById(@PathVariable("id")Long id);
}
