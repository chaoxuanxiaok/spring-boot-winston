package com.winston.controller;

import com.winston.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/*
* 如果要跳转html页面，不能使用 @RestController注解，会解析成json。跳转html页面，需要用@Controller注解
*/
@Controller
public class Thymeleaf_Controller {
    private Logger logger=LoggerFactory.getLogger(Thymeleaf_Controller.class);
    @GetMapping("/all")
    public String all(ModelMap modelMap){
        User u1= new User();
        u1.setAge(18);
        u1.setId(123L);
        u1.setName("longyun");
        u1.setSex(0);
        User u2= new User();
        u2.setAge(13);
        u2.setId(123L);
        u2.setName("heyun");
        u2.setSex(1);
        User u3= new User();
        u3.setAge(14);
        u3.setId(123L);
        u3.setName("zhangyun");
        u3.setSex(1);
        List<User> users=new ArrayList<User>();
        users.add(u1);
        users.add(u2);
        users.add(u3);
        //放入模型
        modelMap.addAttribute("users",users);
        logger.debug(users.toString());
        //返回模板名称（classpath:/templates/目录下的html文件名）
        //注意 返回视图前，确保已经 引入了thymeleaf启动器，能自动注册视图解析器
        //这里表示 跳转到 classpath:/templates/users.html
        return "users";
        }
}
