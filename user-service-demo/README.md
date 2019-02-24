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

​	

