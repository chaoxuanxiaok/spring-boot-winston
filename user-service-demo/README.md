## 说明：提供服务调用方	demo

1.Spring脚手架创建工程

​	脚手架：new module--》Spring Initializr--》选SDK和Default

​	项目信息：

​		Group：com.winston.demo

​		Artifact:user-service-demo

​		Package:com.winston.userservice

​	添加web依赖：web

​	添加mybatis依赖：mysql、jdbc、mybatis

​	填写项目位置：user-service-demo

​	pom.xml文件自动配置：

​		手动引入 通用mapper的依赖

​			<groupId>tk.mybatis</groupId>

​			<artifactId>mapper-spring-boot-starter</artifactId>

​			<version>2.0.2</version>			​		

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

3.配置application文件：服务端口、数据连接池、mybatis别名

4.UserController-->UserService-->UserMapper-->User

5.将user-service注册到Eureka
    --服务上添加Eureka客户端依赖，客户端代码会自动把服务注册到EurekaServer中
    （1）在user-service-demo中
        1）pom.xml文件配置
            添加spring-cloud依赖
            添加spring仓库的地址
            添加Eureka客户端的依赖
        2）启动类上开启Eureka客户端功能
        3）配置application.properties文件
            添加应用名称，以后作为应用的id来使用
            添加Eureka相关配置
            
6.配置完eureka-server1-demo和eureka-server2-demo后
    将user-service注册到Eureka集群
        --配置文件中 将多个注册中心地址用逗号隔开
7.修改配置文件，控制心跳时间（服务续约）
8.
9.
​	

