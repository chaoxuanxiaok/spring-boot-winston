## 说明：服务注册中心	demo

1.Spring脚手架创建工程

​	脚手架：new module--》Spring Initializr--》选SDK和Default

​	项目信息：

​		Group：com.winston.demo

​		Artifact:eureka-server-demo

​		Package:com.winston.eurekaserver

​	添加Cloud Discovery依赖：Eureka Server

​	填写项目位置：eureka-server-demo

​	pom.xml文件自动配置：

​		配置最新的spring-cloud版本
​			<properties>
            		<project.build.sourceEncoding>
            		    UTF-8
            		</project.build.sourceEncoding>
            		<project.reporting.outputEncoding>
            		    UTF-8
            		</project.reporting.outputEncoding>
            		<java.version>
            		    1.8
            		</java.version>
                    <!-- SpringCloud版本，是最新的F系列 -->
            		<spring-cloud.version>
            		    Finchley.RC1
            		</spring-cloud.version>
            </properties>
            
            <dependencyManagement>
            		<dependencies>
                        <!-- SpringCloud依赖，一定要放到dependencyManagement中，起到管理版本的作用即可 -->
            			<dependency>
            				<groupId>org.springframework.cloud</groupId>
            				<artifactId>spring-cloud-dependencies</artifactId>
            				<version>${spring-cloud.version}</version>
            				<type>pom</type>
            				<scope>import</scope>
            			</dependency>
            		</dependencies>
            </dependencyManagement>	
            		​		

2.编写启动类
    声明这个应用是服务注册中心

3.配置文件   ---注意如果有中文注解会报错，解决方案附后
    应用端口、本应用名称、是否也注册自己的信息
    是否拉取其他服务的信息、自己的地址，若集群还要加上其他的地址
     
    setting--File Encodings---三个地方都设为utf-8	
    并勾选Transparent native-to-ascii conversion
4.启动后访问 http://127.0.0.1:10086/

5.将user-service注册到Eureka
    --服务上添加Eureka客户端依赖，客户端代码会自动把服务注册到EurekaServer中
    （1）在user-service-demo中
        1）pom.xml文件配置
            添加spring-cloud依赖
            添加spring仓库的地址
            添加Eureka客户端的依赖
        2）启动类上开启Eureka客户端功能
        3）
    
