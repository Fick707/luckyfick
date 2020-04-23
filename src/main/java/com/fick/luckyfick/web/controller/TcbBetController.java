package com.fick.luckyfick.web.controller;

import com.fick.luckyfick.model.MyBet;
import com.fick.luckyfick.model.WebResult;
import com.fick.luckyfick.service.BetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @name: TcbBetController
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @created: 2020/4/22
 **/
@Slf4j
@RestController
@RequestMapping("/api/tcb/bet")
public class TcbBetController {

    @Autowired
    BetService betService;

    /**
     * 三倍，更精确，更严格的
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/tripleLucky", method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<MyBet> tripleLucky() throws IOException {
        return WebResult.success(betService.tripleLucky());
    }

    /**
     * 两倍，稍严格
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/doubleLucky", method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<MyBet> doubleLucky() throws IOException {
        return WebResult.success(betService.doubleLucky());
    }

    /**
     * 最开放的条件，最随机
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/lucky", method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<MyBet> lucky() throws IOException {
        return WebResult.success(betService.lucky());
    }
}
