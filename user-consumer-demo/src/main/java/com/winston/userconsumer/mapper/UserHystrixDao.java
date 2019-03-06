package com.winston.userconsumer.mapper;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.winston.userconsumer.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserHystrixDao {
    @Autowired
    private RestTemplate restTemplate;

    private static final Logger log =LoggerFactory.getLogger(UserHystrixDao.class);

    /**
     * 当服务出现问题时，失败回滚提示的友好信息
     */
    public User queryUserByIdFallBack(Long id){
        User user = new User();
        user.setId(id);
        user.setName("用户信息查询出现异常");
        return user;
    }

    /**
     * 调用远程服务
     */
    @HystrixCommand(fallbackMethod = "queryUserByIdFallBack")
    public User queryUserById(Long id){ //当该函数执行超时（默认1000ms），会执行fallback函数
        Long start = System.currentTimeMillis();
        String url = "http://user-service-demo/user/"+id;
        User user = this.restTemplate.getForObject(url,User.class);
        Long end = System.currentTimeMillis();
        log.info("访问用时：{}",end-start);          //有点类似python的用法了
        return user;
    }

}
