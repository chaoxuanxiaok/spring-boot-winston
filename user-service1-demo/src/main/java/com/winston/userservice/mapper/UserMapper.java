package com.winston.userservice.mapper;

import com.winston.userservice.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 利用mybatis generator反射数据库生成DAO及Mapper映射文件
 *
 * 因为没有配置mapper接口扫描包，所以每个接口都需要加上 @Mapper注解
 */
@Mapper //该注解会在编译时 为接口生成相应的实现类；     注意：该接口不支持方法重载，因为同名方法会生成相同的id
public interface UserMapper extends tk.mybatis.mapper.common.Mapper<User>{
    //这里直接继承了 通用mapper接口，指定pojo类型，无需自定义方法，直接在 service层执行mapper的方法
}
