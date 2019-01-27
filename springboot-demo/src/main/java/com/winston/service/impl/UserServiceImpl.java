package com.winston.service.impl;

import com.winston.domain.User;
import com.winston.mapper.UserMapper;
import com.winston.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 业务层
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * 注入数据库映射对象
     */
    @Autowired
    private UserMapper userMapper;

    public User queryById(Long id){
        return this.userMapper.selectByPrimaryKey(id);
    }

    /**
     * 通过注解 事务保持一致
     */
    @Transactional
    public void deleteById(Long id){
        this.userMapper.deleteByPrimaryKey(id);
    }
}
