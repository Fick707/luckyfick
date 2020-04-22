package com.fick.luckyfick.common;

import com.alibaba.fastjson.JSON;
import com.fick.luckyfick.BaseTest;
import com.fick.luckyfick.model.Bet;
import com.fick.luckyfick.service.BetService;
import com.fick.luckyfick.tcb.strategy.TcbStrategy;
import com.fick.luckyfick.tcb.strategy.impl.TcbStrategyBuilder;
import com.fick.luckyfick.tcb.strategy.impl.blue.pre.BlueBallPreStrategyExcludeByAppearLastN;
import com.fick.luckyfick.tcb.strategy.impl.blue.pre.BlueBallPreStrategyExcludeByContinuousAppeared;
import com.fick.luckyfick.tcb.strategy.impl.blue.pre.BlueBallPreStrategyIncludeByAbsence;
import com.fick.luckyfick.tcb.strategy.impl.blue.init.BlueBallPreStrategyInitPool;
import com.fick.luckyfick.tcb.strategy.impl.red.pre.RedBallPreStrategyExcludeByAppearLastN;
import com.fick.luckyfick.tcb.strategy.impl.red.pre.RedBallPreStrategyExcludeByContinuousAppeared;
import com.fick.luckyfick.tcb.strategy.impl.red.pre.RedBallPreStrategyIncludeByAbsence;
import com.fick.luckyfick.tcb.strategy.impl.red.init.RedBallPreStrategyInitPool;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @name: BetServiceImplTest
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @created: 2020/4/22
 **/
public class BetServiceImplTest extends BaseTest {

    @Autowired
    BetService betService;

    @Test
    public void testGetOneLuckyBet(){
        List<TcbStrategy> strategies = new TcbStrategyBuilder()
                // red strategies
                // 根据近100次，随机池2000
                .add(new RedBallPreStrategyInitPool(2000,100))
                // 近100次出现次数>=30,则直接排除
                .add(new RedBallPreStrategyExcludeByAppearLastN(100,30))
                // 最近连接出现次数 >= 6,则直接排除
                .add(new RedBallPreStrategyExcludeByContinuousAppeared(6))
                // 最近连接缺失22次，直接选
                .add(new RedBallPreStrategyIncludeByAbsence(22))

                // blue strategies
                .add(new BlueBallPreStrategyInitPool(1000,100))
                // 近100期出现次数>=15,则直接排除
                .add(new BlueBallPreStrategyExcludeByAppearLastN(100,15))
                // 最近连接出现次数 >= 4,则直接排除
                .add(new BlueBallPreStrategyExcludeByContinuousAppeared(4))
                // 最近连接缺失60次，直接选
                .add(new BlueBallPreStrategyIncludeByAbsence(60))
                .build();

        Bet luckyBet = betService.getOneLuckyBet(strategies);
        System.out.println("result:"+ JSON.toJSONString(luckyBet));
    }
}
