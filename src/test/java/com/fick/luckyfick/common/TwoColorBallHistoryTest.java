package com.fick.luckyfick.common;

import com.fick.luckyfick.BaseTest;
import com.fick.luckyfick.biz.TcbHistoryManage;
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
public class TwoColorBallHistoryTest extends BaseTest {

    @Autowired
    TcbHistoryManage tcbHistoryManage;

    @Test
    public void testBetResult(){
        // 获取最近n次的中奖号码
        List<Bet> luckyBets = tcbHistoryManage.getLastBets(1);
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

    @Test
    public void testSecondPrize(){
        // 跑一下历史数据，看看一二等奖是否重复出现过
        List<Bet> luckyBets = tcbHistoryManage.getBetHistory();
        for(Bet checkBet : luckyBets){
            for(Bet luckyBet : luckyBets){
                if(checkBet.getIndex() == luckyBet.getIndex()){
                    continue;
                }
                PrizeType type = BetUtils.getPrizeType(luckyBet,checkBet);
                if(type.ordinal() == 1){
                    System.err.println("first prize appear again."+checkBet.getResult());
                    System.err.println("this.code:"+checkBet.getCode()+"that.code:"+luckyBet.getCode());
                }
                if(type.ordinal() == 2){
                    System.err.println("second prize appear again."+checkBet.getResult());
                    System.err.println("this.code:"+checkBet.getCode()+"that.code:"+luckyBet.getCode());
                }
            }
        }
    }

}
