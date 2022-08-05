package com.fick.luckyfick.service.impl;

import com.fick.luckyfick.model.Bet;
import com.fick.luckyfick.model.MyBet;
import com.fick.luckyfick.model.PrizeType;
import com.fick.luckyfick.service.BetService;
import com.fick.luckyfick.service.HistoryAnalysisService;
import com.fick.luckyfick.service.MyTcbBetService;
import com.fick.luckyfick.tcb.strategy.*;
import com.fick.luckyfick.tcb.strategy.impl.TcbStrategyBuilder;
import com.fick.luckyfick.tcb.strategy.impl.blue.after.BlueBallAfterStrategyExcludeByFirstPrizeAppeared;
import com.fick.luckyfick.tcb.strategy.impl.blue.init.BlueBallPreStrategyInitPool;
import com.fick.luckyfick.tcb.strategy.impl.blue.pre.BlueBallPreStrategyExcludeByAppearLastN;
import com.fick.luckyfick.tcb.strategy.impl.blue.pre.BlueBallPreStrategyExcludeByContinuousAppeared;
import com.fick.luckyfick.tcb.strategy.impl.blue.pre.BlueBallPreStrategyIncludeByAbsence;
import com.fick.luckyfick.tcb.strategy.impl.blue.pre.BlueBallPreStrategyIncludeByAppearLastN;
import com.fick.luckyfick.tcb.strategy.impl.red.after.RedBallAfterStrategyExcludeBySecondPrizeAppeared;
import com.fick.luckyfick.tcb.strategy.impl.red.init.RedBallPreStrategyInitPool;
import com.fick.luckyfick.tcb.strategy.impl.red.pre.RedBallPreStrategyExcludeByAppearLastN;
import com.fick.luckyfick.tcb.strategy.impl.red.pre.RedBallPreStrategyExcludeByContinuousAppeared;
import com.fick.luckyfick.tcb.strategy.impl.red.pre.RedBallPreStrategyIncludeByAbsence;
import com.fick.luckyfick.tcb.strategy.impl.red.pre.RedBallPreStrategyIncludeByAppearLastN;
import com.fick.luckyfick.utils.BetUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @program: luckyfick
 * @description: 投注相关的服务；
 * @author: figo.song
 * @create: 2020/4/6
 **/
@Service
@Slf4j
public class BetServiceImpl implements BetService {

    @Autowired
    private HistoryAnalysisService historyAnalysisService;

    @Autowired
    private MyTcbBetService myTcbBetService;

    @Override
    public boolean isRedBallIn(Bet bet, Integer ballNumber) {
        return BetUtils.isRedBallIn(bet,ballNumber);
    }

    @Override
    public boolean isBlueBallIn(Bet bet, Integer ballNumber) {
        return BetUtils.isBlueBallIn(bet,ballNumber);
    }

    @Override
    public PrizeType getPrizeType(Bet bet, Bet checkBet) {
        return BetUtils.getPrizeType(bet,checkBet);
    }

    @Override
    public MyBet getOneLuckyBet(List<TcbStrategy> strategies) {
        if(strategies == null){
            strategies = new ArrayList<>();
        }
        if(CollectionUtils.isNotEmpty(strategies)){
            strategies.stream().forEach(item -> item.setHistoryAnalysisService(historyAnalysisService).setMyTcbBetService(myTcbBetService));
        }
        // 过滤出红球初始化策略
        List<RedBallInitStrategy> redBallInitStrategies = strategies
                .stream()
                .filter(item -> item instanceof RedBallInitStrategy)
                .map(item -> (RedBallInitStrategy)item)
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(redBallInitStrategies)){
            redBallInitStrategies = new ArrayList<>();
            redBallInitStrategies.add(new RedBallPreStrategyInitPool(1500,100));
        }
        // 过滤出红球预处理策略
        List<RedBallPreStrategy> redBallPreStrategies = strategies
                .stream()
                .filter(item -> item instanceof RedBallPreStrategy)
                .map(item -> (RedBallPreStrategy)item)
                .collect(Collectors.toList());
        // 过滤出红球后处理策略
        List<RedBallAfterStrategy> redBallAfterStrategies = strategies
                .stream()
                .filter(item -> item instanceof RedBallAfterStrategy)
                .map(item -> (RedBallAfterStrategy)item)
                .collect(Collectors.toList());

        // 过滤出蓝球初始化策略
        List<BlueBallInitStrategy> blueBallInitStrategies = strategies
                .stream()
                .filter(item -> item instanceof BlueBallInitStrategy)
                .map(item -> (BlueBallInitStrategy)item)
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(blueBallInitStrategies)){
            blueBallInitStrategies = new ArrayList<>();
            blueBallInitStrategies.add(new BlueBallPreStrategyInitPool(300,100));
        }
        // 过滤出蓝球预处理策略
        List<BlueBallPreStrategy> blueBallPreStrategies = strategies
                .stream()
                .filter(item -> item instanceof BlueBallPreStrategy)
                .map(item -> (BlueBallPreStrategy)item)
                .collect(Collectors.toList());
        // 过滤出蓝球后处理策略
        List<BlueBallAfterStrategy> blueBallAfterStrategies = strategies
                .stream()
                .filter(item -> item instanceof BlueBallAfterStrategy)
                .map(item -> (BlueBallAfterStrategy)item)
                .collect(Collectors.toList());

        TcbStrategyContext luckyContext = new TcbStrategyContext();

        // 先处理红球
        drawRed(luckyContext,redBallInitStrategies,redBallPreStrategies,redBallAfterStrategies);
        drawBlue(luckyContext,blueBallInitStrategies,blueBallPreStrategies,blueBallAfterStrategies);
        return luckyContext.getLuckyBet();
    }

    @Override
    public MyBet tripleLucky() {
        log.info("generate triple lucky bet.");
        List<TcbStrategy> strategies = new TcbStrategyBuilder()
                // red strategies
                // 根据近100次，随机池2000
                .add(new RedBallPreStrategyInitPool(1000,50))
                // 近100次出现次数>=30,则直接排除
                .add(new RedBallPreStrategyExcludeByAppearLastN(100,30))
                // 最近连接出现次数 >= 6,则直接排除
                .add(new RedBallPreStrategyExcludeByContinuousAppeared(6))
                // 最近连接缺失22次，直接选
                .add(new RedBallPreStrategyIncludeByAbsence(22))
                // 近100将出现次数<=9,则直接添加
                .add(new RedBallPreStrategyIncludeByAppearLastN(100,9))
                // 如果与历史二等奖重复，直接放弃
                .add(new RedBallAfterStrategyExcludeBySecondPrizeAppeared())

                // blue strategies
                .add(new BlueBallPreStrategyInitPool(200,100))

                // 近100期出现次数>=15,则直接排除
                .add(new BlueBallPreStrategyExcludeByAppearLastN(100,12))
                // 最近连接出现次数 >= 4,则直接排除
                .add(new BlueBallPreStrategyExcludeByContinuousAppeared(4))
                // 最近连接缺失60次，直接选
                .add(new BlueBallPreStrategyIncludeByAbsence(90))

                // 如果与历史一等奖重复，直接放弃
                .add(new BlueBallAfterStrategyExcludeByFirstPrizeAppeared())
                .build();
        MyBet tripleLucky = getOneLuckyBet(strategies);
        tripleLucky.setMultiple(3);
        return tripleLucky;
    }

    @Override
    public MyBet doubleLucky() {
        log.info("generate double lucky bet.");
        List<TcbStrategy> strategies = new TcbStrategyBuilder()
                // red strategies
                // 根据近100次，随机池3000
                .add(new RedBallPreStrategyInitPool(1000,50))
                // 近100次出现次数>=60,则直接排除
                .add(new RedBallPreStrategyExcludeByAppearLastN(100,50))
                // 最近连接出现次数 >= 7,则直接排除
                .add(new RedBallPreStrategyExcludeByContinuousAppeared(7))
                // 最近连接缺失40次，直接选
                .add(new RedBallPreStrategyIncludeByAbsence(40))
                // 如果与历史二等奖重复，直接放弃
                .add(new RedBallAfterStrategyExcludeBySecondPrizeAppeared())

                // blue strategies
                .add(new BlueBallPreStrategyInitPool(200,100))
                // 近100期出现次数>=15,则直接排除
                .add(new BlueBallPreStrategyExcludeByAppearLastN(100,15))
                // 最近连接出现次数 >= 4,则直接排除
                .add(new BlueBallPreStrategyExcludeByContinuousAppeared(4))

                // 最近100次出现次数 < 3 则直接选
                .add(new BlueBallPreStrategyIncludeByAppearLastN(100,3))
                // 最近连接缺失60次，直接选
                .add(new BlueBallPreStrategyIncludeByAbsence(60))
                // 如果与历史一等奖重复，直接放弃
                .add(new BlueBallAfterStrategyExcludeByFirstPrizeAppeared())
                .build();
        MyBet doubleLucky = getOneLuckyBet(strategies);
        doubleLucky.setMultiple(1);
        return doubleLucky;
    }

    @Override
    public MyBet lucky() {
        log.info("generate lucky bet.");
        List<TcbStrategy> strategies = new TcbStrategyBuilder()
                // red strategies
                // 根据近100次，随机池5000
                .add(new RedBallPreStrategyInitPool(1000,50))
                // 近100次出现次数>=50,则直接排除
                .add(new RedBallPreStrategyExcludeByAppearLastN(100,50))
                // 最近连接出现次数 >= 7,则直接排除
                .add(new RedBallPreStrategyExcludeByContinuousAppeared(7))

                // blue strategies
                .add(new BlueBallPreStrategyInitPool(200,100))
                // 近100期出现次数>=35,则直接排除
                .add(new BlueBallPreStrategyExcludeByAppearLastN(100,30))
                // 最近连接出现次数 >= 4,则直接排除
                .add(new BlueBallPreStrategyExcludeByContinuousAppeared(4))

                // 最近100次出现次数 < 3 则直接选
                .add(new BlueBallPreStrategyIncludeByAppearLastN(100,1))

                .build();
        MyBet lucky = getOneLuckyBet(strategies);
        lucky.setMultiple(1);
        return lucky;
    }

    /**
     * 抽红球
     * @param context
     * @param preStrategies
     * @param afterStrategies
     */
    private void drawRed(TcbStrategyContext context,List<RedBallInitStrategy> initStrategies,List<RedBallPreStrategy> preStrategies,List<RedBallAfterStrategy> afterStrategies){
        if(CollectionUtils.isNotEmpty(initStrategies)){
            initStrategies.stream().forEach(redBallInitStrategy -> redBallInitStrategy.bingo(context));
        }
        if(CollectionUtils.isNotEmpty(preStrategies)){
            preStrategies.stream().forEach(redBallPreStrategy -> redBallPreStrategy.bingo(context));
        }
        // 排除选池中的排除球
        if(CollectionUtils.isNotEmpty(context.getRedBallExcluded())){
            context.setRedBallPool(context.getRedBallPool().stream().filter(item -> !context.getRedBallExcluded().contains(item)).collect(Collectors.toList()));
        }
        while(!context.isRedBallBingo()) {
            // 执行从选池中选6个数
            // 先包含一些必选的
            List<Integer> redBalls = Lists.newArrayList(context.getRedBallIncluded());
            int poolSize = context.getRedBallPool().size();
            Random random = new Random();
            while(redBalls.size() < 6){
                Collections.shuffle(context.getRedBallPool());
                int index = random.nextInt(poolSize);
                if(!redBalls.contains(context.getRedBallPool().get(index))){
                    redBalls.add(context.getRedBallPool().get(index));
                }
            }
            Collections.sort(redBalls);
            context.getLuckyBet().setRed1(redBalls.get(0));
            context.getLuckyBet().setRed2(redBalls.get(1));
            context.getLuckyBet().setRed3(redBalls.get(2));
            context.getLuckyBet().setRed4(redBalls.get(3));
            context.getLuckyBet().setRed5(redBalls.get(4));
            context.getLuckyBet().setRed6(redBalls.get(5));
            context.setRedBallBingo(true);
            // 后置处理,后置处理中，可能将red ball bingo 设置为false
            if (CollectionUtils.isNotEmpty(afterStrategies)) {
                afterStrategies.stream().forEach(redBallAfterStrategy -> redBallAfterStrategy.bingo(context));
            }
        }
    }

    /**
     * 抽蓝球
     * @param context
     * @param preStrategies
     * @param afterStrategies
     */
    private void drawBlue(TcbStrategyContext context,List<BlueBallInitStrategy> blueBallInitStrategies,List<BlueBallPreStrategy> preStrategies,List<BlueBallAfterStrategy> afterStrategies){
        if(CollectionUtils.isNotEmpty(blueBallInitStrategies)){
            blueBallInitStrategies.stream().forEach(blueBallInitStrategy -> blueBallInitStrategy.bingo(context));
        }
        if(CollectionUtils.isNotEmpty(preStrategies)){
            preStrategies.stream().forEach(blueBallPreStrategy -> blueBallPreStrategy.bingo(context));
        }
        // 排除选池中的排除球
        if(CollectionUtils.isNotEmpty(context.getBlueBallExcluded())){
            context.setBlueBallPool(context.getBlueBallPool().stream().filter(item -> !context.getBlueBallExcluded().contains(item)).collect(Collectors.toList()));
        }
        while(!context.isBlueBallBingo()) {
            // 执行从选池中选1个数
            // 先包含一些必选的
            List<Integer> blueBalls = Lists.newArrayList(context.getBlueBallIncluded());
            if(CollectionUtils.isNotEmpty(blueBalls)){
                context.getLuckyBet().setBlue1(blueBalls.get(0));
                context.setBlueBallBingo(true);
            } else {
                int poolSize = context.getBlueBallPool().size();
                Random random = new Random();
                while (blueBalls.size() < 1) {
                    Collections.shuffle(context.getBlueBallPool());
                    int index = random.nextInt(poolSize);
                    if (!blueBalls.contains(context.getBlueBallPool().get(index))) {
                        blueBalls.add(context.getBlueBallPool().get(index));
                    }
                }
                Collections.sort(blueBalls);
                context.getLuckyBet().setBlue1(blueBalls.get(0));
                context.setBlueBallBingo(true);
            }
            // 后置处理,后置处理中，可能将blue ball bingo 设置为false
            if (CollectionUtils.isNotEmpty(afterStrategies)) {
                afterStrategies.stream().forEach(blueBallAfterStrategy -> blueBallAfterStrategy.bingo(context));
            }
        }
    }
}
