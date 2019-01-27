package com.winston.config.jdbcConfig_2;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * config文件的第二种配置方式
 * 再把读取完的属性类  配置datasource
 *
 *
 * 同第一种比较的优势：
 * Relaxed binding：松散绑定
 * 不严格要求属性文件中的属性名与成员变量名一致。
 * 支持驼峰、中划线、下划线等转换，乃至支持对象引导。
 * user.friend.name     代表user对象中的friend属性中的name属性，此处的friend也是对象，@Value无法完成注入
 *
 * meta-data support:元数据支持，帮助IDE生成属性提示
 */
@Configuration                                              //声明这个类作为配置类，代替xml
@EnableConfigurationProperties(JdbcProperties.class)        //声明要使用JdbcProperties类的对象
public class JdbcConfig_2 {

    @Bean
    public DataSource dataSource(JdbcProperties jp){
        DruidDataSource dataSource2=new DruidDataSource();
        dataSource2.setUrl(jp.getUrl());
        dataSource2.setDriverClassName(jp.getDriverClassName());
        dataSource2.setUsername(jp.getUsername());
        dataSource2.setPassword(jp.getPassword());
        return dataSource2;
    }
}
