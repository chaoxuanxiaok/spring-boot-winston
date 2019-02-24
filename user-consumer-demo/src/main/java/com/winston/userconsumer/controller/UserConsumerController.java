package com.winston.userconsumer.controller;

import com.winston.userconsumer.pojo.User;
import com.winston.userconsumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("consumer")
public class UserConsumerController {
    @Autowired
    private UserService userService;

    /**
     * 这次访问改成了 get请求
     * 需要使用 http://localhost:8082/consumer?ids=1 进行访问
     * 注意区分 @PathVariable这个注解的用法
     * @param ids
     * @return
     */
    @GetMapping
    public List<User> consumer(@RequestParam("ids")List<Long> ids){
        return this.userService.queryUserById(ids);
    }
}
