package com.fick.luckyfick.web.controller;

import com.fick.luckyfick.model.Bet;
import com.fick.luckyfick.model.WebResult;
import com.fick.luckyfick.service.BetService;
import com.fick.luckyfick.tcb.strategy.TcbStrategy;
import com.fick.luckyfick.tcb.strategy.impl.TcbStrategyBuilder;
import com.fick.luckyfick.tcb.strategy.impl.blue.after.BlueBallAfterStrategyExcludeByFirstPrizeAppeared;
import com.fick.luckyfick.tcb.strategy.impl.blue.init.BlueBallPreStrategyInitPool;
import com.fick.luckyfick.tcb.strategy.impl.blue.pre.BlueBallPreStrategyExcludeByAppearLastN;
import com.fick.luckyfick.tcb.strategy.impl.blue.pre.BlueBallPreStrategyExcludeByContinuousAppeared;
import com.fick.luckyfick.tcb.strategy.impl.blue.pre.BlueBallPreStrategyIncludeByAbsence;
import com.fick.luckyfick.tcb.strategy.impl.red.after.RedBallAfterStrategyExcludeBySecondPrizeAppeared;
import com.fick.luckyfick.tcb.strategy.impl.red.init.RedBallPreStrategyInitPool;
import com.fick.luckyfick.tcb.strategy.impl.red.pre.RedBallPreStrategyExcludeByAppearLastN;
import com.fick.luckyfick.tcb.strategy.impl.red.pre.RedBallPreStrategyExcludeByContinuousAppeared;
import com.fick.luckyfick.tcb.strategy.impl.red.pre.RedBallPreStrategyIncludeByAbsence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

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
    public WebResult<Bet> tripleLucky() throws IOException {
        List<TcbStrategy> strategies = new TcbStrategyBuilder()
                // red strategies
                // 根据近100次，随机池2000
                .add(new RedBallPreStrategyInitPool(2000,100))
                // 近100次出现次数>=30,则直接排除
                .add(new RedBallPreStrategyExcludeByAppearLastN(100,30))
                // 近100将出现次数<=9,则直接添加
                .add(new RedBallPreStrategyIncludeByAbsence(9))
                // 最近连接出现次数 >= 6,则直接排除
                .add(new RedBallPreStrategyExcludeByContinuousAppeared(6))
                // 最近连接缺失22次，直接选
                .add(new RedBallPreStrategyIncludeByAbsence(22))
                // 如果与历史二等奖重复，直接放弃
                .add(new RedBallAfterStrategyExcludeBySecondPrizeAppeared())

                // blue strategies
                .add(new BlueBallPreStrategyInitPool(1000,100))
                // 近100期出现次数>=15,则直接排除
                .add(new BlueBallPreStrategyExcludeByAppearLastN(100,15))
                // 最近连接出现次数 >= 4,则直接排除
                .add(new BlueBallPreStrategyExcludeByContinuousAppeared(4))
                // 最近连接缺失60次，直接选
                .add(new BlueBallPreStrategyIncludeByAbsence(60))
                // 如果与历史一等奖重复，直接放弃
                .add(new BlueBallAfterStrategyExcludeByFirstPrizeAppeared())
                .build();

        Bet luckyBet = betService.getOneLuckyBet(strategies);
        return WebResult.success(luckyBet);
    }

    /**
     * 两倍，稍严格
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/doubleLucky", method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<Bet> doubleLucky() throws IOException {
        List<TcbStrategy> strategies = new TcbStrategyBuilder()
                // red strategies
                // 根据近100次，随机池3000
                .add(new RedBallPreStrategyInitPool(3000,100))
                // 近100次出现次数>=60,则直接排除
                .add(new RedBallPreStrategyExcludeByAppearLastN(100,50))
                // 最近连接出现次数 >= 7,则直接排除
                .add(new RedBallPreStrategyExcludeByContinuousAppeared(7))
                // 最近连接缺失40次，直接选
                .add(new RedBallPreStrategyIncludeByAbsence(40))
                // 如果与历史二等奖重复，直接放弃
                .add(new RedBallAfterStrategyExcludeBySecondPrizeAppeared())

                // blue strategies
                .add(new BlueBallPreStrategyInitPool(2000,100))
                // 近100期出现次数>=15,则直接排除
                .add(new BlueBallPreStrategyExcludeByAppearLastN(100,15))
                // 最近连接出现次数 >= 4,则直接排除
                .add(new BlueBallPreStrategyExcludeByContinuousAppeared(4))
                // 最近连接缺失60次，直接选
                .add(new BlueBallPreStrategyIncludeByAbsence(100))
                // 如果与历史一等奖重复，直接放弃
                .add(new BlueBallAfterStrategyExcludeByFirstPrizeAppeared())
                .build();

        Bet luckyBet = betService.getOneLuckyBet(strategies);
        return WebResult.success(luckyBet);
    }

    /**
     * 最开放的条件，最随机
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/lucky", method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<Bet> lucky() throws IOException {
        List<TcbStrategy> strategies = new TcbStrategyBuilder()
                // red strategies
                // 根据近100次，随机池5000
                .add(new RedBallPreStrategyInitPool(5000,100))
                // 近100次出现次数>=50,则直接排除
                .add(new RedBallPreStrategyExcludeByAppearLastN(100,50))
                // 最近连接出现次数 >= 7,则直接排除
                .add(new RedBallPreStrategyExcludeByContinuousAppeared(7))

                // blue strategies
                .add(new BlueBallPreStrategyInitPool(2000,100))
                // 近100期出现次数>=35,则直接排除
                .add(new BlueBallPreStrategyExcludeByAppearLastN(100,30))
                // 最近连接出现次数 >= 4,则直接排除
                .add(new BlueBallPreStrategyExcludeByContinuousAppeared(4))
                .build();

        Bet luckyBet = betService.getOneLuckyBet(strategies);
        return WebResult.success(luckyBet);
    }
}
