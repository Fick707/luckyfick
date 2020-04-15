package com.fick.luckyfick.web.model.result;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @create: 2020/4/15
 **/
@Data
@ToString
public class UserLoginResult implements Serializable {
    private static final long serialVersionUID = -4660532947533599566L;

    /**
     * 登录类型，原样返回
     */
    private String type;

    /**
     * 当前用户的权限
     */
    private List<String> currentAuthority;

    /**
     * 登录结果状态，OK为成功
     */
    private String status;

    private String token;
}
