package com.fick.twocolorball.web.controller;

import com.fick.twocolorball.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @name: HistoryAnalysisController
 * @program: twocolorball
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
    public String topRed(HttpServletRequest request,
                         @RequestParam(value = "userName",required = true) String userName,
                         @RequestParam(value = "password")String password) throws IOException {
        return userService.login(userName,password);
    }

}
