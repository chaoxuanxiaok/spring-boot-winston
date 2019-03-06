package com.winston.userconsumer.mapper.impl;

import com.winston.userconsumer.mapper.UserFeignClient;
import com.winston.userconsumer.pojo.User;
import org.springframework.stereotype.Component;

/**
 * 这个实现类 重写的方法 都是当Feign客户端调用超时，执行的fallback函数
 */
@Component  //但凡是类要被扫描到都需要注解啊
public class UserFeignClientFallback implements UserFeignClient {

    @Override
    public User queryUserById(Long id) {
        User user = new User();
        user.setId(id);
        user.setName("用户信息查询出现异常");
        return user;
    }
}
