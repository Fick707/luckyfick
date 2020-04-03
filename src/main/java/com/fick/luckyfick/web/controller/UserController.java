package com.fick.luckyfick.web.controller;

import com.fick.luckyfick.service.UserService;
import com.fick.luckyfick.web.constants.WebConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public String login(HttpServletResponse response,
                         @RequestParam(value = "userName") String userName,
                         @RequestParam(value = "password")String password) throws IOException {
        String token = userService.login(userName,password);
        if(StringUtils.isNotBlank(token)){
            Cookie cookie = new Cookie(WebConstants.CommonHttpHeader.HTTP_HEADER_KEY_TOKEN,token);
            cookie.setPath("/");
            cookie.setMaxAge(2 * 60 * 60);
            response.addCookie(cookie);
            return token;
        } else {
            return "user name or password error.";
        }
    }

    /**
     * 登出
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "logout",method = {RequestMethod.POST,RequestMethod.GET})
    public String logout(HttpServletResponse response) throws IOException {
        userService.logout();
        Cookie cookie = new Cookie(WebConstants.CommonHttpHeader.HTTP_HEADER_KEY_TOKEN,null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "logout done.";
    }

}
