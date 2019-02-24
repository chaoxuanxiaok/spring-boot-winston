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

