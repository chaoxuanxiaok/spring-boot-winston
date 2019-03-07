## 说明：服务注册中心	demo

1.Spring脚手架创建工程

​	脚手架：new module--》Spring Initializr--》选SDK和Default

​	项目信息：

​		Group：com.winston.demo

​		Artifact:zuul-demo

​		Package:com.winston.zuuldemo

​	添加Cloud Routing依赖：zuul

​	填写项目位置：zuul-demo

​	pom.xml文件自动配置：

​2.编写启动类，开启Zuul功能

3.配置文件
    定义网关的 服务端口和服务名
    
---------------------若一个服务只有一个实例，可以把映射地址写死---------  
4.配置文件
    编写路由规则：
        先去注册中心的 控制面板查看服务的状态、ip与端口
        在网关的配置文件中设置映射规则
            
---------------------若一个服务有多个实例，应该借助Eureka获取服务地址---
5.pom.xml引入Eureka的依赖
6.启动类开启 Eureka客户端发现功能
7.配置文件 添加Eureka配置，获取服务信息
8.配置文件 修改映射配置，通过服务名称获取

------------------------------------------------------------------------
9.通过zuul提供的过滤器实现鉴权
    创建ZuulFilter，这个类引入的jar包中已有，这里创建只是为了方便对重要方法做笔记
10.自定义过滤器LoginFilter，模拟登陆校验

11.Zuul默认集成 Ribbon和Hystrix，但所有策略都走得默认值，需要手动进行配置