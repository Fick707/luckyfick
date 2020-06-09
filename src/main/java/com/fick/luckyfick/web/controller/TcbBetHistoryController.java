package com.fick.luckyfick.web.controller;

import com.fick.luckyfick.model.*;
import com.fick.luckyfick.service.HistoryAnalysisService;
import com.fick.luckyfick.service.MyTcbBetService;
import com.fick.luckyfick.utils.BetUtils;
import com.fick.luckyfick.web.model.param.BetParam;
import com.fick.luckyfick.web.model.param.HistoryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @name: TcbBetController
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @created: 2020/4/22
 **/
@Slf4j
@RestController
@RequestMapping("/api/tcb/bet/history")
public class TcbBetHistoryController {

    @Autowired
    HistoryAnalysisService historyAnalysisService;

    @Autowired
    MyTcbBetService myTcbBetService;

    /**
     * 最新的开奖记录
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/latest", method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<List<Bet>> latest(@RequestBody HistoryParam param) throws IOException {
        if(param == null){
            param = new HistoryParam();
        }
        if(param.getLast() == null){
            param.setLast(1);
        }
        return WebResult.success(historyAnalysisService.getLastBetHistory(param.getLast()));
    }

    /**
     * 两倍，稍严格
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/myLatest", method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<List<MyBet>> myLatest() throws IOException {
        return WebResult.success(myTcbBetService.getMyLatestBet());
    }

    @RequestMapping(value = "/my", method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<List<MyBetResult>> myLatest(@RequestBody BetParam param) throws IOException {

        Bet bet = historyAnalysisService.getLuckyBetByCode(param.getCode());
        List<MyBet> mys = myTcbBetService.getMyBet(param.getCode());

        return WebResult.success(mys.stream().map(i -> {
            MyBetResult r = new MyBetResult();
            BeanUtils.copyProperties(i,r);
            PrizeType p = BetUtils.getPrizeType(bet,i);
            r.setPrizeName(p.getName());
            r.setPrizeAmount(p.getAmount());
            return  r;
        }).collect(Collectors.toList()));
    }


}
