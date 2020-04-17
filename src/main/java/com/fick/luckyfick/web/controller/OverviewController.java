package com.fick.luckyfick.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.fick.luckyfick.model.WebResult;
import com.fick.luckyfick.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/tcbOverview", method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<JSONObject> tcbOverview() throws IOException {
        JSONObject overview = new JSONObject();
        overview.put("balance",1000);
        overview.put("betCount",12);
        return WebResult.success(overview);
    }

}
