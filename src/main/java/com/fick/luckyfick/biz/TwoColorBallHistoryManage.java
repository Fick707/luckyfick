package com.fick.luckyfick.biz;

import com.fick.luckyfick.dao.TwoColorBallHistoryDAO;
import com.fick.luckyfick.model.Bet;
import com.fick.luckyfick.utils.BetUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @create: 2020/3/29
 **/
@Component
@Slf4j
public class TwoColorBallHistoryManage {

    @Autowired
    TwoColorBallHistoryDAO twoColorBallHistoryDAO;

    /**
     * 历史中奖记录
     */
    private List<Bet> betHistory = new ArrayList<>();

    @PostConstruct
    public void init(){
        log.info("init two color ball history ...");
        loadHistory();
        log.info("init two color ball history done.");
    }

    public List<Bet> getBetHistory(){
        return betHistory;
    }

    /**
     * 从历史文件中加载历史
     */
    public void loadHistory() {
        betHistory = twoColorBallHistoryDAO.loadHistory();
    }

    /**
     * 获取最近last次开奖结果
     * @param last
     * @return
     */
    public List<Bet> getLastBets(int last){
        List<Bet> betList = getBetHistory();
        if(CollectionUtils.isEmpty(betList)){
            return Lists.newArrayList();
        }
        if(betList.size() > last){
            betList = betList.subList(betList.size() - last,betList.size());
        }
        return betList;
    }

    /**
     * 根据历史倒推，获取指定红色球号最近缺失的次数
     * @param ballNumber
     * @return
     */
    public Integer getRedBallMissCount(Integer ballNumber){
        List<Bet> his = getBetHistory();
        Integer missCount = 0;
        for(int i = his.size() - 1 ; i >= 0 ; i --){
            if(BetUtils.isRedBallIn(his.get(i),ballNumber)){
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
    public Integer getBlueBallMissCount(Integer ballNumber){
        List<Bet> his = getBetHistory();
        Integer missCount = 0;
        for(int i = his.size() - 1 ; i >= 0 ; i --){
            if(BetUtils.isBlueBallIn(his.get(i),ballNumber)){
                return missCount;
            }
            missCount ++;
        }
        return missCount;
    }

    /**
     * 获取指定红球历史上最大连接出现次数
     * @param ballNumber
     * @return
     */
    public int getRedMaxAppearCount(Integer ballNumber){
        int maxAppearCount = 0;
        int currentAppearCount = 0;
        List<Bet> betList = getBetHistory();
        for(Bet bet : betList){
            if(!BetUtils.isRedBallIn(bet,ballNumber)){
                if(currentAppearCount > maxAppearCount){
                    maxAppearCount = currentAppearCount;
                }
                currentAppearCount = 0;
            } else {
                currentAppearCount ++;
            }
        }
        return maxAppearCount;
    }

    /**
     * 获取指定蓝球历史上最大连接出现次数
     * @param ballNumber
     * @return
     */
    public int getBlueMaxAppearCount(Integer ballNumber){
        int maxAppearCount = 0;
        int currentAppearCount = 0;
        List<Bet> betList = getBetHistory();
        for(Bet bet : betList){
            if(!BetUtils.isBlueBallIn(bet,ballNumber)){
                if(currentAppearCount > maxAppearCount){
                    maxAppearCount = currentAppearCount;
                }
                currentAppearCount = 0;
            } else {
                currentAppearCount ++;
            }
        }
        return maxAppearCount;
    }

    /**
     * 获取指定红球历史上最大的缺失次数
     * @param ballNumber
     * @return
     */
    public int getRedMaxMissCount(Integer ballNumber){
        int maxMissCount = 0;
        int currentMissCount = 0;
        List<Bet> betList = getBetHistory();
        for(Bet bet : betList){
            if(BetUtils.isRedBallIn(bet,ballNumber)){
                if(currentMissCount > maxMissCount){
                    maxMissCount = currentMissCount;
                }
                currentMissCount = 0;
            } else {
                currentMissCount ++;
            }
        }
        return maxMissCount;
    }

    /**
     * 获取指定蓝球历史上最大的连续缺失次数
     * @param ballNumber
     * @return
     */
    public int getBlueMaxMissCount(Integer ballNumber){
        int maxMissCount = 0;
        int currentMissCount = 0;
        List<Bet> betList = getBetHistory();
        for(Bet bet : betList){
            if(BetUtils.isBlueBallIn(bet,ballNumber)){
                if(currentMissCount > maxMissCount){
                    maxMissCount = currentMissCount;
                }
                currentMissCount = 0;
            } else {
                currentMissCount ++;
            }
        }
        return maxMissCount;
    }

    /**
     * 指定红色球号历史缺失次数
     * @param ballNumber
     * @return
     */
    public List<Integer> redBallHisMissCounts(Integer ballNumber){
        List<Integer> missCounts = new ArrayList<>();
        int currentMissCount = 0;
        List<Bet> betList = getBetHistory();
        for(Bet bet : betList){
            if(BetUtils.isRedBallIn(bet,ballNumber)){
                missCounts.add(currentMissCount);
                currentMissCount = 0;
            } else {
                currentMissCount ++;
            }
        }
        return missCounts;
    }

    /**
     * 指定蓝色球号历史缺失次数
     * @param ballNumber
     * @return
     */
    public List<Integer> blueBallHisMissCounts(Integer ballNumber){
        List<Integer> missCounts = new ArrayList<>();
        int currentMissCount = 0;
        List<Bet> betList = getBetHistory();
        for(Bet bet : betList){
            if(BetUtils.isBlueBallIn(bet,ballNumber)){
                missCounts.add(currentMissCount);
                currentMissCount = 0;
            } else {
                currentMissCount ++;
            }
        }
        return missCounts;
    }


}
