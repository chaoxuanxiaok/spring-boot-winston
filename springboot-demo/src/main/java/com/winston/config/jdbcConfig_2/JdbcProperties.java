package com.winston.config.jdbcConfig_2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * config文件的第二种配置方式
 * 先新建一个类 用属性去读取properties文件中的值
 */
@ConfigurationProperties(prefix="jdbc")     //声明当前类为属性读取类；且读取属性文件中，前缀为jdbc的值
@PropertySource("classpath:jdbc.properties")    //同样此处需要指定文件位置，否则需要 改名为application.properties
public class JdbcProperties {

    //属性名称  必须与属性文件key中 jdbc.前缀后面部分的一致
    private String url;
    private String driverClassName;
    private String username;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
