package com.winston.service;

import com.winston.domain.User;

public interface UserService {

    public User queryById(Long id);
    public void deleteById(Long id);


}
