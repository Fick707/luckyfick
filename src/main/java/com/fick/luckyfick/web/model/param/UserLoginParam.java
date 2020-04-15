package com.fick.luckyfick.web.model.param;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @create: 2020/4/15
 **/
@Data
@ToString
public class UserLoginParam implements Serializable {
    private static final long serialVersionUID = 2932745643631575609L;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 登录类型，account,phone ...
     */
    private String type;
}
