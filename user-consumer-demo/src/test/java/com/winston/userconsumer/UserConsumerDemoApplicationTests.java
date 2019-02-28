package com.winston.userconsumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserConsumerDemoApplicationTests {

    @Autowired
    RibbonLoadBalancerClient client;//这个类是用来进行负载均衡的

    @Test
    public void loadBanlanceTest() {
        for (int i=0;i<100;i++){
            //choose方法 是负载均衡选择获取服务实例
            ServiceInstance instance =this.client.choose("user-service-demo");
            System.out.println(instance.getHost()+":"+instance.getPort());
        }
    }

}
