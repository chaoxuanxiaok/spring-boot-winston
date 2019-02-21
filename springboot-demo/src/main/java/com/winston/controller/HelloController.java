package com.winston.controller;

import com.winston.domain.User;
import com.winston.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;


/**
 *
 */
@RestController
public class HelloController {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private DataSource dataSource2;

    @Autowired
    private UserService userService;

    @GetMapping("hello")
    public String hello(){
        return "hello,Spring boot"+dataSource+dataSource2;
    }

    @GetMapping("/selectUser")
    public User selectUser(){
        User u1= new User();
        u1.setAge(18);
        u1.setId(123L);
        u1.setName("longyun");
        u1.setSex(1);
        return u1;
    }
    }

