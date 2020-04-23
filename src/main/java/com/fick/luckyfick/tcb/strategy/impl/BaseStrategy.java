package com.fick.luckyfick.tcb.strategy.impl;

import com.fick.luckyfick.model.Bet;
import com.fick.luckyfick.model.MyBet;
import com.fick.luckyfick.model.PrizeType;
import com.fick.luckyfick.service.HistoryAnalysisService;
import com.fick.luckyfick.tcb.strategy.TcbStrategy;
import com.fick.luckyfick.utils.BetUtils;

/**
 * @name: BaseStrategy
 * @program: luckyfick
 * @description: 策略基类
 * @author: figo.song
 * @created: 2020/4/22
 **/
public abstract class BaseStrategy implements TcbStrategy {

    /**
     * 引入历史分析服务
     */
    protected HistoryAnalysisService historyAnalysisService;

    @Override
    public void setHistoryAnalysisService(HistoryAnalysisService historyAnalysisService){
        this.historyAnalysisService = historyAnalysisService;
    }

    /**
     * 是否与历史一等奖相同
     */
    protected boolean isSameFirstPrizeAppeared(Bet bet){
        for(Bet luckyBet : historyAnalysisService.getBetHistory()){
            PrizeType prizeType = BetUtils.getPrizeType(luckyBet,bet);
            if( prizeType.ordinal() == PrizeType.First.ordinal() ){
                return true;
            }
        }
        return false;
    }

    /**
     * 是否与历史一等奖相同
     */
    protected boolean isSameFirstPrizeAppeared(MyBet bet){
        for(Bet luckyBet : historyAnalysisService.getBetHistory()){
            PrizeType prizeType = BetUtils.getPrizeType(luckyBet,bet);
            if( prizeType.ordinal() == PrizeType.First.ordinal() ){
                return true;
            }
        }
        return false;
    }
    /**
     * 是否与历史二等奖相同
     */
    protected boolean isSameSecondPrizeAppeared(Bet bet){
        for(Bet luckyBet : historyAnalysisService.getBetHistory()){
            PrizeType prizeType = BetUtils.getPrizeType(luckyBet,bet);
            if( prizeType.ordinal() == PrizeType.Second.ordinal() ){
                return true;
            }
        }
        return false;
    }
    /**
     * 是否与历史二等奖相同
     */
    protected boolean isSameSecondPrizeAppeared(MyBet bet){
        for(Bet luckyBet : historyAnalysisService.getBetHistory()){
            PrizeType prizeType = BetUtils.getPrizeType(luckyBet,bet);
            if( prizeType.ordinal() == PrizeType.Second.ordinal() ){
                return true;
            }
        }
        return false;
    }
}
