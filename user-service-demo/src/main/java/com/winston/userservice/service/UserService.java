package com.winston.userservice.service;

import com.winston.userservice.mapper.UserMapper;
import com.winston.userservice.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 这里简单demo，就直接使用 class，没有用interface
 * 用@Service注解，并没有指定 该类的实例名
 */
@Service
public class UserService {
    //注入时 userMapper会语法报错
    //Settings - Editor - Inspections - Spring - Spring Core - Code - Autowiring for Bean Class - disable
    // （idea2017好像是把√去掉）
    @Autowired
    private UserMapper userMapper;

    public User queryById(Long id) throws InterruptedException {//虽然userMapper没有定义该方法，编译时会自动生成
        //加入0-2000毫秒的随机休眠，演示超时现象
        Thread.sleep(
                new Random().nextInt(2000)
        );
        return this.userMapper.selectByPrimaryKey(id);
    }
}

