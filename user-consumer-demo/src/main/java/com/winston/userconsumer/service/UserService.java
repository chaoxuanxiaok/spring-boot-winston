package com.winston.userconsumer.service;

import com.winston.userconsumer.mapper.UserDao;
import com.winston.userconsumer.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * service的实现类 用@Service
 * dao的实现类 用@Component
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    //循环查询 userDao的信息
    public List<User> queryUserById(List<Long> ids){
        List<User> users = new ArrayList<>();
        for(Long id:ids){
            User user = this.userDao.queryUserById(id);
            users.add(user);
        }
        return users;
    }
}
