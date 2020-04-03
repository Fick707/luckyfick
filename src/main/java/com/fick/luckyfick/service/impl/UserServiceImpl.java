package com.fick.luckyfick.service.impl;

import com.fick.common.utils.good.UUIDUtil;
import com.fick.luckyfick.service.UserService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @name: UserServiceImpl
 * @program: luckyfick
 * @description: 用户服务实现
 * @author: figo.song
 * @created: 2020/3/30
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${com.fick.luckyfick.admin.username}")
    private String adminUserName;

    @Value("${com.fick.luckyfick.admin.password}")
    private String adminPassword;

    private Cache<String,String> tokenCache = CacheBuilder.newBuilder()
            .maximumSize(10)
            .expireAfterWrite(2, TimeUnit.HOURS)
            .build();

    @Override
    public String login(String userName, String password) {
        if( ! StringUtils.equals(userName, adminUserName) || ! StringUtils.equals(password, adminPassword)){
            return null;
        }
        try {
            String token = tokenCache.get(userName, () -> UUIDUtil.get32UUID());
            return token;
        } catch (ExecutionException e) {
            log.error("get token from cache error.",e);
            return null;
        }
    }

    @Override
    public void logout() {
        tokenCache.invalidate(adminUserName);
    }

    @Override
    public boolean checkToken(String token) {
        String value = tokenCache.getIfPresent(adminUserName);
        return StringUtils.isNotBlank(value) && StringUtils.equals(token,value);
    }
}
