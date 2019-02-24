package com.winston.userconsumer.mapper;

import com.winston.userconsumer.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 这里不是调用mapper查询数据库，而是通过restTemplate远程调用
 * 如果是interface就用@Mapper注解，继承通用mapper实现对数据库的操作
 * 如果是class就用@Component注解，做普通的远程调用处理
 */
@Component   //必须加了注解，才能被扫描到
public class UserDao {
    //将启动类注册的restTemplate注入
    @Autowired
    private RestTemplate restTemplate;

    public User queryUserById(Long id){
        //此处暴露的问题是什么？要访问的地址硬编码到了代码中，不方便后期维护
        String url = "http://localhost:8081/user/"+id;
        //远程调用指定地址的接口，封装成User对象
        return this.restTemplate.getForObject(url,User.class);
    }
}
