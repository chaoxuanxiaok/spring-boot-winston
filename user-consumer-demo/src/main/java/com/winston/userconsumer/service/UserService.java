package com.winston.userconsumer.service;

import com.winston.userconsumer.mapper.UserDao;
import com.winston.userconsumer.mapper.UserFeignClient;
import com.winston.userconsumer.mapper.UserHystrixDao;
import com.winston.userconsumer.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private UserHystrixDao userHystrixDao;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserFeignClient userFeignClient;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    /**
     * 第三阶段 ：通过负载均衡利用服务名称去自动选择实例
     * @param ids
     * @return
     */
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

    /**
     * 第四阶段：加入熔断机制，当调用服务超时，调用fallback函数，返回友好提示
     * @param ids
     * @return
     */
    public List<User> queryUserById4(List<Long> ids){
        List<User> list = new ArrayList<>();
        //箭头左边是 入参，右边是 执行的代码块
        ids.forEach(id ->{
            list.add(this.userHystrixDao.queryUserById(id));
        });

        return list;
    }

    /**
     * 第五阶段：通过Feign调用
     */
    public List<User> queryUserById5(List<Long> ids){
        List<User> users = new ArrayList<>();
        try{
            ids.forEach(id->{
                users.add(this.userFeignClient.queryUserById(id));
            });
        }catch(Exception e){
            log.info("调用服务失败");
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
