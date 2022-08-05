package com.fick.luckyfick.web.controller;

import com.fick.luckyfick.constants.ResultCode;
import com.fick.luckyfick.model.WebResult;
import com.fick.luckyfick.service.UserService;
import com.fick.luckyfick.utils.HttpUtils;
import com.fick.luckyfick.web.constants.WebConstants;
import com.fick.luckyfick.web.model.param.UserLoginParam;
import com.fick.luckyfick.web.model.result.UserDetailResult;
import com.fick.luckyfick.web.model.result.UserLoginResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @name: HistoryAnalysisController
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @created: 2020/4/1
 **/
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<UserLoginResult> login(HttpServletResponse response, @RequestBody UserLoginParam param) throws IOException {
        String token = userService.login(param.getUserName(),param.getPassword());
        if(StringUtils.isNotBlank(token)){
            Cookie cookie = new Cookie(WebConstants.CommonHttpHeader.HTTP_HEADER_KEY_TOKEN,token);
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cookie);
            UserLoginResult loginResult = new UserLoginResult();
            loginResult.setType(param.getType());
            loginResult.setCurrentAuthority(Arrays.asList("admin"));
            loginResult.setStatus("OK");
            loginResult.setToken(token);
            return WebResult.success(loginResult);
        } else {
            return WebResult.failure(ResultCode.PARAM_ERROR_CODE.getCode(),ResultCode.PARAM_ERROR_CODE.getValue());
        }
    }

    /**
     * 登出
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/logout",method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<String> logout(HttpServletResponse response) throws IOException {
        userService.logout();
        Cookie cookie = new Cookie(WebConstants.CommonHttpHeader.HTTP_HEADER_KEY_TOKEN,null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return WebResult.success(ResultCode.SERVER_SUCCESS_CODE.getValue());
    }

    /**
     * 登出
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/currentUser",method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<UserDetailResult> currentUser() throws IOException {
        return WebResult.success(new UserDetailResult());
    }

    /**
     * 查询我的ip
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/myIp",method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<String> myIp(HttpServletRequest request) throws IOException {
        return WebResult.success(HttpUtils.getRealIp(request));
    }

}
