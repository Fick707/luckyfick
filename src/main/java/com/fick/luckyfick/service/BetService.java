package com.fick.luckyfick.service;

import com.fick.luckyfick.model.Bet;
import com.fick.luckyfick.model.PrizeType;

/**
 * @program: luckyfick
 * @description: 投注服务
 * @author: figo.song
 * @create: 2020/4/6
 **/
public interface BetService {

    /**
     * 判断指定红球是否在开奖号码中
     * @param bet
     * @param ballNumber
     * @return
     */
    boolean isRedBallIn(Bet bet,Integer ballNumber);

    /**
     * 判断指定蓝球是否在开奖号码中
     * @param bet
     * @param ballNumber
     * @return
     */
    boolean isBlueBallIn(Bet bet, Integer ballNumber);

    /**
     * 根据指定投注和开奖结果，判断中奖级别
     * @param bet 开奖结果
     * @param checkBet 要检查的投注
     * @return
     */
    PrizeType getPrizeType(Bet bet, Bet checkBet);
}
