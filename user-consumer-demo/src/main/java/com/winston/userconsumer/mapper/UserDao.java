package com.winston.userconsumer.mapper;

import com.winston.userconsumer.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
    //将 启动类通过注解开启的 Eureka客户端  注入
    @Autowired
    private DiscoveryClient discoveryClient;    //注意这里导包 不要netflix，而是cloud.client.discovery包下的

    /**
     * 第一阶段：采取硬编码去访问
     * 这种以前硬编码的做法，与下面一种注册服务后的方法比较
     */
//    public User queryUserById(Long id){
//        //此处暴露的问题是什么？要访问的地址硬编码到了代码中，不方便后期维护
//        String url = "http://localhost:8081/user/"+id;
//        //远程调用指定地址的接口，封装成User对象
//        return this.restTemplate.getForObject(url,User.class);
//    }

    /**
     *第二阶段：采用 注册中心的发现客户端 去获取服务实例调用
     * @param id
     * @return
     */
    public User queryUserById(Long id){
        //根据服务名称，获取服务实例，可能是集群，list来接收
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("user-service-demo");
        //考虑到只有一个服务实例，所以直接获取
        ServiceInstance serviceInstance = serviceInstances.get(0);
        //再获取实例的 ip和端口信息
        String url = "http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/user/"+id;
        //以上的三行代码 其实放到service层比较好，这样通过ids来请求时，不用每次都获取这个url

        //远程调用指定地址的接口，封装成User对象
        return this.restTemplate.getForObject(url,User.class);
    }
}
