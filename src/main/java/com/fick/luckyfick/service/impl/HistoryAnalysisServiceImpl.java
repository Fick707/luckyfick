package com.fick.luckyfick.service.impl;

import com.alibaba.fastjson.JSON;
import com.fick.luckyfick.biz.TwoColorBallHistoryManage;
import com.fick.luckyfick.model.BallCount;
import com.fick.luckyfick.model.BallCountTrend;
import com.fick.luckyfick.model.BallMissCountTrend;
import com.fick.luckyfick.model.Bet;
import com.fick.luckyfick.service.BetService;
import com.fick.luckyfick.service.HistoryAnalysisService;
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

    @Autowired
    BetService betService;

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
        List<Integer> counts = new ArrayList<>();
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
            counts.add(to);
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
        List<Integer> counts = new ArrayList<>();
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
            counts.add(to);
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
        List<Bet> betList = historyManage.getLastBets(last);
        return generateTopRed(betList);
    }

    @Override
    public List<BallCount> getTopBlueInLast(int last) {
        List<Bet> betList = historyManage.getLastBets(last);
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
        return historyManage.getRedBallMissCount(ballNumber);
    }

    @Override
    public Integer getBlueBallMissCountInLast(Integer ballNumber) {
        return historyManage.getBlueBallMissCount(ballNumber);
    }

    @Override
    public List<BallCount> getRedBallMissCounts() {
        List<BallCount> redMissCounts = new ArrayList<>();
        for(int i = 1 ; i <= 33 ; i ++ ){
            BallCount ballMissCount = new BallCount();
            ballMissCount.setBallNumber(i);
            ballMissCount.setCount(historyManage.getRedBallMissCount(i));
            redMissCounts.add(ballMissCount);
        }
        Collections.sort(redMissCounts, Comparator.comparing(BallCount::getCount).reversed());
        return redMissCounts;
    }

    @Override
    public List<BallCount> getBlueBallMissCounts() {
        List<BallCount> blueMissCounts = new ArrayList<>();
        for(int i = 1 ; i <= 16 ; i ++ ){
            BallCount ballMissCount = new BallCount();
            ballMissCount.setBallNumber(i);
            ballMissCount.setCount(historyManage.getBlueBallMissCount(i));
            blueMissCounts.add(ballMissCount);
        }
        Collections.sort(blueMissCounts, Comparator.comparing(BallCount::getCount).reversed());
        return blueMissCounts;
    }

    @Override
    public List<BallCount> getRedBallHisMaxMissCounts() {
        List<BallCount> redHisMissCounts = new ArrayList<>();
        for(int i = 1 ; i <= 33 ; i ++ ){
            BallCount ballMissCount = new BallCount();
            ballMissCount.setBallNumber(i);
            ballMissCount.setCount(historyManage.getRedMaxMissCount(i));
            redHisMissCounts.add(ballMissCount);
        }
        Collections.sort(redHisMissCounts, Comparator.comparing(BallCount::getCount).reversed());
        return redHisMissCounts;
    }

    @Override
    public List<BallCount> getBlueBallHisMaxMissCounts() {
        List<BallCount> blueHisMissCounts = new ArrayList<>();
        for(int i = 1 ; i <= 16 ; i ++ ){
            BallCount ballMissCount = new BallCount();
            ballMissCount.setBallNumber(i);
            ballMissCount.setCount(historyManage.getBlueMaxMissCount(i));
            blueHisMissCounts.add(ballMissCount);
        }
        Collections.sort(blueHisMissCounts, Comparator.comparing(BallCount::getCount).reversed());
        return blueHisMissCounts;
    }

    @Override
    public BallMissCountTrend getRedBallHisMissCounts() {
        Map<Integer,List<Integer>> missCountMap = new HashMap<>();
        for(int i = 1 ; i <= 33 ; i ++ ){
            missCountMap.put(i,historyManage.redBallHisMissCounts(i));
        }
        BallMissCountTrend ballMissCountTrend = new BallMissCountTrend();
        List<Integer> ballNumbers = new ArrayList<>();
        ballNumbers.addAll(missCountMap.keySet());
        ballMissCountTrend.setBallNumberMissCountsMap(missCountMap);
        return ballMissCountTrend;
    }

    @Override
    public BallMissCountTrend getBlueBallHisMissCounts() {
        Map<Integer,List<Integer>> missCountMap = new HashMap<>();
        for(int i = 1 ; i <= 16 ; i ++ ){
            missCountMap.put(i,historyManage.blueBallHisMissCounts(i));
        }
        BallMissCountTrend ballMissCountTrend = new BallMissCountTrend();
        List<Integer> ballNumbers = new ArrayList<>();
        ballNumbers.addAll(missCountMap.keySet());
        ballMissCountTrend.setBallNumberMissCountsMap(missCountMap);
        return ballMissCountTrend;
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
