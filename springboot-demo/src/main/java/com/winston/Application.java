package com.winston;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 */

/**
 *---------1-----------------
 * 被三个重要注解修饰
 * @SpringBootConfiguration     声明当前类是SpringBoot的配置类，项目中只能有一个。 Spring会自动扫描到配置了@Configuration的类，并读取其中的配置信息
 * @EnableAutoConfiguration     告诉SpringBoot添加了哪些依赖，根据依赖猜测我们想如何配置spring。 如果引进web，就自动帮忙完成spring和springMVC的默认配置
 * @ComponentScan                 默认从声明这个注解的类所在包开始，扫描包及子包       可通过basePackageClasses或basePackages属性来指定要扫描的包
 *
 *spring-boot是如何帮我们自动配置的？
 * 项目中的spring-boot-autoconfigure的依赖包中定义了大量自动配置类，  涵盖主流的开源框架
 * 以springMVC的配置类为例，有四个关键注解：
 *      @Configuration：
 *          声明这个类是配置类
 *      @ConditionalOnWebApplication(type=Type.SERVLET)
 *          满足项目的类是ype.SERVLET类型，也就是普通web工程的 条件时，配置才会生效
 *      @ConditionalOnClass({Servlet.class,DispatcherServlet.class,WebMvcConfigurer.class})
 *          满足 Servlet,DispatcherServlet,WebMvcConfigurer三个类存在时，配置才会生效
 *          也就是引入 tomcat和springMVC的相关依赖后，才会生效
 *      @ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
 *          当不存在    WebMVCConfigurationSupport的类时，配置才会生效
 *          所以我们可以通过自定义 WebMVCConfigurationSupport ，springboot关于springMVC的默认配置就会失效
 *
 * 这些默认配置类的属性注入 也是通过xxxProperties的属性注入，
 * 如果我们要覆盖这些默认属性，需要在application.properties中定义与其前缀prefix和字段名一致的属性
 */
@SpringBootApplication          //--------------1------------------------
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);  //-----------------------2------------------------
    }
}

