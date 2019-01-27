package com.winston.domain;

import java.io.Serializable;

/**
 * domain类 是与数据库映射的实体类  相当于以前的dto
 * 将实体数据序列化发送到客户端时，先将domain类转成一个model，再将model序列化 作为响应给浏览器的数据
 */
public class User implements Serializable {

    private static final long serialVersionUID =-637479967158798064L;
    private String name;
    private String sex;
    private String age;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", id=" + id +
                '}';
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
