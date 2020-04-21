package com.fick.luckyfick.common;

import com.fick.luckyfick.BaseTest;
import com.fick.luckyfick.biz.TwoColorBallHistoryManage;
import com.fick.luckyfick.model.Bet;
import com.fick.luckyfick.model.PrizeType;
import com.fick.luckyfick.utils.BetUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @name: BetTest
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @created: 2020/4/20
 **/
public class BetTest extends BaseTest {

    @Autowired
    TwoColorBallHistoryManage twoColorBallHistoryManage;

    @Test
    public void testBetResult(){
        // 获取最近n次的中奖号码
        List<Bet> luckyBets = twoColorBallHistoryManage.getLastBets(1);
        List<Bet> toCheck = new ArrayList<>();
        toCheck.add(betOne(1,4,6,24,25,28,15));
        toCheck.add(betOne(6,8,12,15,25,32,15));
        toCheck.add(betOne(5,10,11,20,28,32,13));
        for(Bet luckyBet : luckyBets){
            toCheck.stream().forEach(item -> {
                PrizeType type = BetUtils.getPrizeType(luckyBet,item);
                if(type.getAmount() > 0){
                    System.err.println("lucky bet:\t" + luckyBet.getResult() + "\nm   y bet:\t" + item.getResult());
                    System.err.println("result : " + type.getName());
                } else {
                    System.out.println("lucky bet:\t" + luckyBet.getResult() + "\nm   y bet:\t" + item.getResult());
                    System.out.println("result : " + type.getName());
                }
            });
        }
    }

    private Bet betOne(Integer red1,Integer red2,Integer red3,Integer red4,Integer red5, Integer red6,Integer blue1){
        Bet bet = new Bet();
        bet.setRed1(red1);
        bet.setRed2(red2);
        bet.setRed3(red3);
        bet.setRed4(red4);
        bet.setRed5(red5);
        bet.setRed6(red6);
        bet.setBlue1(blue1);
        return bet;
    }

}
