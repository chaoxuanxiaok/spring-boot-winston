## 说明：服务调用方	demo

1.Spring脚手架创建工程

​	脚手架：new module--》Spring Initializr--》选SDK和Default

​	项目信息：

​		Group：com.winston.demo

​		Artifact:user-consumer-demo

​		Package:com.winston.userconsumer

​	添加web依赖：web

​	填写项目位置：user-consumer-demo

​	pom.xml文件自动配置：

​		手动引入 OKHttp的依赖

​			<groupId>com.squareup.okhttp3</groupId>

​			<artifactId>okhttp</artifactId>

​			<version>3.9.0</version>			​		

2.项目结构

​	java

​		controller

​		service

​		mapper

​		pojo

​	resources

​		static

​		templates

​		application.properties

3.配置application文件：服务端口

4.UserConsumerController-->UserService-->UserMapper-->User


通过user-service-demo和user-consumer-demo两个简单demo暴露的问题：
    （1）在consumer中，把url地址硬编码到代码中，不便后期维护
            服务管理--如何实现动态路由
    （2）consumer中需要记忆user-service的地址，如果变更后没有通知，地址会失效
            服务管理--如何自动注册和发现
    （3）consumer不清楚服务提供方的状态，服务宕机也不知道
            服务管理--如何实现状态监管
    （4）user-service只有一台服务，不具备高可用性
            服务如何解决容灾问题
    （5）即使user-service形成集群，consumer还需自己实现负载均衡
            服务如何实现负载均衡
            服务如何实现统一配置
​
-----------------------订阅服务----------------------------------	
6.consumer-demo从Eureka订阅服务(确定已经注册服务了)
      --服务上添加Eureka客户端依赖，通过服务名称获取信息
      在user-consumer-demo中
      （1）pom.xml文件配置
          添加spring-cloud依赖
          添加spring仓库的地址
          添加Eureka客户端的依赖
      （2）启动类上开启Eureka客户端功能
      （3）配置application.properties文件
              添加应用名称，以后作为应用的id来使用
              添加Eureka相关配置
       (4)修改mapper层代码
              用DiscoveryClient类的方法，根据服务名称，获取服务实例，区别于之前的硬编码

7.修改配置文件，控制 获取服务地址列表的频率

----------------------负载均衡-------------------------------------
8.在启动类注册RestTemplate类时，添加@LoadBalanced注解，使用Eureka的ribbon组件功能
9.在service或mapper 直接通过服务名称去调用

10.测试类 对RibbonLoadBalanceClient进行测试，追溯原理

11.配置文件中更改负载均衡策略

12.增强RestTemplate的重试能力----  一台服务宕机，重试调用另一台服务
   配置文件
   引入spring-retry依赖

----------------------熔断机制-------------------------------------   
13.增加熔断机制
    pom.xml中引入Hystrix依赖
    在启动类上添加注解@EnableHystrix，开启熔断器功能
    添加一个用来访问user服务的Dao，并声明一个失败时的回滚处理函数
    在UserService中调用这个Dao，替代之前的调用    
14.在配置文件中，设置Hystrix超时时间，需要大于Ribbon的超时时间

---------------------声明式、模板式HTTP客户端调用---------------------
15.增加Feign客户端
    pom.xml中引入Feign依赖--openfeign
    创建接口 xxxFeignClient，伪装成controller
    修改UserService，不再调用UserDao，而是调用xxxFeignClient
    启动类添加注解，开启Feign功能
16.Feign中已经继承Ribbon依赖和自动配置，无需单独引入依赖和注册RestTemplate
    直接在配置文件中通过 服务名.ribbon.xxx去对超时时间等参数配置
17.Feign也集成了Hystrix
    默认是关闭的，需要配置文件中开启
    对Fallback进行配置
        自定义一个类xxxFeignClientFallback，实现上面编写的xxxFeignClient，作为Fallback的处理类
        在xxxFeignClient中，指定刚才编写的实现类
18.请求压缩
    配置文件中  开启请求压缩、开启响应压缩
    配置文件中   对请求的数据类型、触发压缩的大小下限 进行设置
19.日志级别
    logging.level.xx=debug能设置日志级别，但对Feign客户端无效
    @FeignClient修改的客户端在被代理时，会创建一个新的Feign.Logger实例，需要额外指定日志级别
    （1）定义 logging.level.包名=debug
    （2）创建配置类FeignConfig
    （3）在xxxFeignClient中，指定配置类
    
---------------------------------------------------------------------------
    
