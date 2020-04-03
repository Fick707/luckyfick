package com.fick.luckyfick.service;

/**
 * @name: UserService
 * @program: luckyfick
 * @description: user service
 * @author: figo.song
 * @created: 2020/3/30
 **/
public interface UserService {

    /**
     * 登录并返回token
     * @param userName
     * @param password
     * @return 登录成功，返回token,登录失败或异常，返回null;
     */
    String login(String userName,String password);

    /**
     * 用户登出
     */
    void logout();

    /**
     * 校验指定用户的token的有效性
     * @param token
     * @return
     */
    boolean checkToken(String token);
}
