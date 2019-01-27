package com.winston.mapper;

import com.winston.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 利用mybatis generator反射数据库生成DAO及Mapper映射文件
 *
 * 因为没有配置mapper接口扫描包，所以每个接口都需要加上 @Mapper注解
 */
@Mapper //该注解会在编译时 为接口生成相应的实现类；     注意：该接口不支持方法重载，因为同名方法会生成相同的id
public interface UserMapper {
   public User selectByPrimaryKey(Long id);
   public void deleteByPrimaryKey(Long id);


    @Select("select * from user where name = #{name}")
    public User find(String name);

    @Select("select * from user where name = #{name} and pwd = #{pwd}")
    /**
     * 对于多个参数来说，每个参数之前都要加上@Param注解，
     * 要不然会找不到对应的参数进而报错
     */
    public User login(@Param("name")String name, @Param("pwd")String pwd);



}
