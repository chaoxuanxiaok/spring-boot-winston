package com.winston.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * config文件的第一种配置方式
 *
 * 直接通过属性注入：指定properties文件 将key与该配置类属性匹配 将值一一注入
 */
@Configuration                                      //声明这个类作为配置类，代替xml文件
@PropertySource("classpath:jdbc.properties")        //指定外部属性文件
public class JdbcConfig_1 {

    @Value("${jdbc.url}")       //为属性注入值
    String url;
    @Value("${jdbc.driverClassName}")
    String driverClassName;
    @Value("${jdbc.username}")
    String username;
    @Value("${jdbc.password}")
    String password;

    /**
     * @Bean 将DataSource()方法声明为 一个注册Bean的方法，spring会自动调用该方法，将方法的返回值加入spring容器中
     * 相当于 在spring.xml中配置了 <bean></bean>标签
     * 之后就可以在各个地方 通过 @Autowired 注入DataSource
     */
    @Bean
    public DataSource dataSource(){
        DruidDataSource datasource=new DruidDataSource();
        datasource.setUrl(url);
        datasource.setDriverClassName(driverClassName);
        datasource.setUsername(username);
        datasource.setPassword(password);

        return datasource;
    }
}
