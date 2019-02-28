package com.winston.userconsumer.service;

import com.winston.userconsumer.mapper.UserDao;
import com.winston.userconsumer.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private RestTemplate restTemplate;

    //
    public List<User> queryUserByIds(List<Long> ids) {
        List<User> users = new ArrayList<>();
        String url = "http://user-service-demo/user/";
        for (Long id : ids) {
            users.add(this.restTemplate.getForObject(url + id, User.class));
            try {
                Thread.sleep(500);//间隔500ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            return users;
        }

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
