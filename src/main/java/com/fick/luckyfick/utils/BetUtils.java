package com.fick.luckyfick.utils;

import com.fick.luckyfick.model.Bet;
import com.fick.luckyfick.model.PrizeType;

/**
 * @program: luckyfick
 * @description: 投注相关的工具
 * @author: figo.song
 * @create: 2020/4/6
 **/
public class BetUtils {

    /**
     * 红球是否在中奖号码中
     * @param bet
     * @param ballNumber
     * @return
     */
    public static boolean isRedBallIn(Bet bet, Integer ballNumber) {
        return bet.getRed1().intValue() == ballNumber
                || bet.getRed2().intValue() == ballNumber
                || bet.getRed3().intValue() == ballNumber
                || bet.getRed4().intValue() == ballNumber
                || bet.getRed5().intValue() == ballNumber
                || bet.getRed6().intValue() == ballNumber;
    }

    /**
     * 蓝球是否在中奖号码中
     * @param bet
     * @param ballNumber
     * @return
     */
    public static boolean isBlueBallIn(Bet bet, Integer ballNumber) {
        return bet.getBlue1().intValue() == ballNumber;
    }

    /**
     * 根据指定投注和开奖结果，判断中奖级别
     * @param bet
     * @param checkBet
     * @return
     */
    public static PrizeType getPrizeType(Bet bet, Bet checkBet){
        int redBingoCount = 0;
        int blueBingoCount = 0;
        if(isBlueBallIn(bet,checkBet.getBlue1())){
            blueBingoCount ++;
        }
        if(isRedBallIn(bet,checkBet.getRed1())){
            redBingoCount ++;
        }
        if(isRedBallIn(bet,checkBet.getRed2())){
            redBingoCount ++;
        }
        if(isRedBallIn(bet,checkBet.getRed3())){
            redBingoCount ++;
        }
        if(isRedBallIn(bet,checkBet.getRed4())){
            redBingoCount ++;
        }
        if(isRedBallIn(bet,checkBet.getRed5())){
            redBingoCount ++;
        }
        if(isRedBallIn(bet,checkBet.getRed6())){
            redBingoCount ++;
        }
        if(redBingoCount == 6 && blueBingoCount == 1){
            return PrizeType.First;
        }
        if(redBingoCount == 6 && blueBingoCount == 0){
            return PrizeType.Second;
        }
        if(redBingoCount == 5 && blueBingoCount == 1){
            return PrizeType.Third;
        }
        if((redBingoCount == 5 && blueBingoCount == 0) || (redBingoCount == 4 && blueBingoCount == 1)){
            return PrizeType.Fourth;
        }
        if((redBingoCount == 4 && blueBingoCount == 0) || (redBingoCount == 3 && blueBingoCount == 1)){
            return PrizeType.Fifth;
        }
        if(redBingoCount < 3 && blueBingoCount == 1){
            return PrizeType.Sixth;
        }

        return PrizeType.Missed;
    }
}
