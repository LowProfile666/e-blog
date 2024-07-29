package com.zsm.service.impl;

import com.zsm.bean.Article;
import com.zsm.bean.User;
import com.zsm.mapper.UserMapper;
import com.zsm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author : ZSM
 * Time :  2024/06/29
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User check(User user) {
        return userMapper.check(user);
    }

}
