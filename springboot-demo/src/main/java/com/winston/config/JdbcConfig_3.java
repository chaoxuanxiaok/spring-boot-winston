package com.winston.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * config文件的第三种配置方式
 *
 * 更优雅，但是有先决条件
 */
@Configuration                  //声明该类是配置类，代替xml文件
public class JdbcConfig_3 {
    @Bean
    @ConfigurationProperties(prefix = "jdbc")       //读取 application.properties文件中前缀为jdbc的值
    public DataSource dataSource(){                 //前提是被注入的类 一定要有属性与文件中jdbc前缀后的key值名称一致，且有set方法
        //springboot会自动调用这个Bean的set方法，完成注入
        DruidDataSource dataSource3=new DruidDataSource();
        return dataSource3;
    }
}
