package com.fick.luckyfick.service.impl;

import com.alibaba.fastjson.JSON;
import com.fick.luckyfick.biz.TwoColorBallHistoryManage;
import com.fick.luckyfick.model.BallCount;
import com.fick.luckyfick.model.BallCountTrend;
import com.fick.luckyfick.model.Bet;
import com.fick.luckyfick.service.HistoryAnalysisService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: luckyfick
 * @description: 历史中奖号码分析服务
 * @author: figo.song
 * @create: 2020/3/29
 **/
@Service
@Slf4j
public class HistoryAnalysisServiceImpl implements HistoryAnalysisService {

    @Autowired
    TwoColorBallHistoryManage historyManage;

    @Override
    public List<Integer> getTopRed() {
        List<Bet> betList = historyManage.getBetHistory();
        List<BallCount> ballCounts = generateTopRed(betList);
        log.info("red top:{}.", JSON.toJSON(ballCounts));
        return ballCounts.stream().map(BallCount::getBallNumber).collect(Collectors.toList());
    }

    @Override
    public List<Integer> getTopBlue() {
        List<Bet> betList = historyManage.getBetHistory();
        List<BallCount> ballCounts = generateTopBlue(betList);
        log.info("blue top:{}.", JSON.toJSON(ballCounts));
        return ballCounts.stream().map(BallCount::getBallNumber).collect(Collectors.toList());
    }

    @Override
    public BallCountTrend getRedBallCountTrend(int step) {
        int from = 0;
        int to = from + step;
        List<Bet> bets = historyManage.getBetHistory();
        List<String> counts = new ArrayList<>();
        Map<Integer,List<Integer>> ballNumberCountsMap = new HashMap<>();
        while (to < bets.size()){
            List<BallCount> ballCounts = generateTopRed(bets.subList(from,to));
            for(BallCount ballCount : ballCounts){
                Integer number = ballCount.getBallNumber();
                List<Integer> countList = ballNumberCountsMap.get(number);
                if(countList == null){
                    countList = new ArrayList<>();
                    ballNumberCountsMap.put(number,countList);
                }
                countList.add(ballCount.getCount());
            }
            counts.add(to+"");
            from += step;
            to = from + step;
        }

        BallCountTrend result = new BallCountTrend();
        result.setCounts(counts);
        result.setBallNumberCountsMap(ballNumberCountsMap);
        return result;
    }

    @Override
    public BallCountTrend getBlueBallCountTrend(int step) {
        int from = 0;
        int to = from + step;
        List<Bet> bets = historyManage.getBetHistory();
        List<String> counts = new ArrayList<>();
        Map<Integer,List<Integer>> ballNumberCountsMap = new HashMap<>();
        while (to < bets.size()){
            List<BallCount> ballCounts = generateTopBlue(bets.subList(from,to));
            for(BallCount ballCount : ballCounts){
                Integer number = ballCount.getBallNumber();
                List<Integer> countList = ballNumberCountsMap.get(number);
                if(countList == null){
                    countList = new ArrayList<>();
                    ballNumberCountsMap.put(number,countList);
                }
                countList.add(ballCount.getCount());
            }
            counts.add(to+"");
            from += step;
            to = from + step;
        }
        BallCountTrend result = new BallCountTrend();
        result.setCounts(counts);
        result.setBallNumberCountsMap(ballNumberCountsMap);
        return result;
    }


    @Override
    public List<BallCount> getTopRedInLast(int last) {
        List<Bet> betList = getLastBets(last);
        return generateTopRed(betList);
    }

    @Override
    public List<BallCount> getTopBlueInLast(int last) {
        List<Bet> betList = getLastBets(last);
        return generateTopBlue(betList);
    }

    @Override
    public Integer getRedBallCountInLast(int last, Integer ballNumber) {
        List<BallCount> redBallCounts = getTopRedInLast(last);
        if(CollectionUtils.isEmpty(redBallCounts)){
            return 0;
        }
        for(BallCount ballCount : redBallCounts){
            if(ballCount.getBallNumber().intValue() == ballNumber){
                return ballCount.getCount();
            }
        }
        return 0;
    }

    @Override
    public Integer getBlueBallCountInLast(int last, Integer ballNumber) {
        List<BallCount> blueBallCounts = getTopBlueInLast(last);
        if(CollectionUtils.isEmpty(blueBallCounts)){
            return 0;
        }
        for(BallCount ballCount : blueBallCounts){
            if(ballCount.getBallNumber().intValue() == ballNumber){
                return ballCount.getCount();
            }
        }
        return 0;
    }

    @Override
    public Integer getRedBallMissCountInLast(Integer ballNumber) {
        return getRedBallMissCount(ballNumber);
    }

    @Override
    public Integer getBlueBallMissCountInLast(Integer ballNumber) {
        return getBlueBallMissCount(ballNumber);
    }

    @Override
    public List<BallCount> getRedBallMissCounts() {
        List<BallCount> redMissCounts = new ArrayList<>();
        for(int i = 1 ; i <= 33 ; i ++ ){
            BallCount ballMissCount = new BallCount();
            ballMissCount.setBallNumber(i);
            ballMissCount.setCount(getRedBallMissCount(i));
            redMissCounts.add(ballMissCount);
        }
        Collections.sort(redMissCounts, Comparator.comparing(BallCount::getCount));
        return redMissCounts;
    }

    @Override
    public List<BallCount> getBlueBallMissCounts() {
        List<BallCount> blueMissCounts = new ArrayList<>();
        for(int i = 1 ; i <= 16 ; i ++ ){
            BallCount ballMissCount = new BallCount();
            ballMissCount.setBallNumber(i);
            ballMissCount.setCount(getBlueBallMissCount(i));
            blueMissCounts.add(ballMissCount);
        }
        Collections.sort(blueMissCounts, Comparator.comparing(BallCount::getCount));
        return blueMissCounts;
    }


    /**
     * 根据历史倒推，获取指定红色球号最近缺失的次数
     * @param ballNumber
     * @return
     */
    private Integer getRedBallMissCount(Integer ballNumber){
        List<Bet> his = historyManage.getBetHistory();
        Integer missCount = 0;
        for(int i = his.size() - 1 ; i >= 0 ; i --){
            Bet bet = his.get(i);
            if(bet.getRed1().intValue() == ballNumber
            || bet.getRed2().intValue() == ballNumber
            || bet.getRed3().intValue() == ballNumber
            || bet.getRed4().intValue() == ballNumber
            || bet.getRed5().intValue() == ballNumber
            || bet.getRed6().intValue() == ballNumber
            ){
                return missCount;
            }
            missCount ++;
        }
        return missCount;
    }

    /**
     * 根据历史倒推，获取指定蓝色球号最近缺失的次数
     * @param ballNumber
     * @return
     */
    private Integer getBlueBallMissCount(Integer ballNumber){
        List<Bet> his = historyManage.getBetHistory();
        Integer missCount = 0;
        for(int i = his.size() - 1 ; i >= 0 ; i --){
            Bet bet = his.get(i);
            if(bet.getBlue1().intValue() == ballNumber){
                return missCount;
            }
            missCount ++;
        }
        return missCount;
    }

    private List<Bet> getLastBets(int last){
        List<Bet> betList = historyManage.getBetHistory();
        if(CollectionUtils.isEmpty(betList)){
            return Lists.newArrayList();
        }
        if(betList.size() > last){
            betList = betList.subList(betList.size() - last,betList.size());
        }
        return betList;
    }

    private List<BallCount> generateTopRed(List<Bet> betList) {
        Map<Integer,Integer> countMap = new HashMap<>(33);
        if(CollectionUtils.isNotEmpty(betList)){
            betList.stream().forEach(item -> {
                increase(item.getRed1(),countMap);
                increase(item.getRed2(),countMap);
                increase(item.getRed3(),countMap);
                increase(item.getRed4(),countMap);
                increase(item.getRed5(),countMap);
                increase(item.getRed6(),countMap);
            });
        }
        return convert2BallCountList(countMap);
    }

    private List<BallCount> convert2BallCountList(Map<Integer,Integer> countMap){
        List<BallCount> ballCounts = new ArrayList<>();
        for(Integer number : countMap.keySet()){
            BallCount ballCount = new BallCount();
            ballCount.setBallNumber(number);
            ballCount.setCount(countMap.get(number));
            ballCounts.add(ballCount);
        }
        Collections.sort(ballCounts, Comparator.comparing(BallCount::getCount).reversed());
        return ballCounts;
    }

    private List<BallCount> generateTopBlue(List<Bet> betList) {
        Map<Integer,Integer> countMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(betList)){
            betList.stream().forEach(item -> {
                increase(item.getBlue1(),countMap);
            });
        }
        return convert2BallCountList(countMap);
    }

    private void increase(Integer number,Map<Integer,Integer> countMap){
        Integer count = countMap.get(number);
        if(count == null){
            count = 0;
        }
        count ++;
        countMap.put(number,count);
    }

}
