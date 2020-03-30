package com.fick.twocolorball.service.impl;

import com.fick.twocolorball.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @name: UserServiceImpl
 * @program: twocolorball
 * @description: 用户服务实现
 * @author: figo.song
 * @created: 2020/3/30
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {



    @Override
    public String login(String userName, String password) {
        return null;
    }

    @Override
    public String checkToken(String token) {
        return null;
    }
}
