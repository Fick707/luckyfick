package com.fick.luckyfick.tcb.strategy.impl.red.after;

import com.fick.luckyfick.tcb.strategy.RedBallAfterStrategy;
import com.fick.luckyfick.tcb.strategy.TcbStrategyContext;
import com.fick.luckyfick.tcb.strategy.impl.BaseStrategy;

/**
 * @name: RedBallAfterStrategyExcludeBySecondPrizeAppeared
 * @program: luckyfick
 * @description: 二等奖出现过的，排除，因为蓝球后出，可以在这里判断所有球
 * @author: figo.song
 * @created: 2020/4/22
 **/
public class RedBallAfterStrategyExcludeBySecondPrizeAppeared extends BaseStrategy implements RedBallAfterStrategy {

    @Override
    public void bingo(TcbStrategyContext context) {
        context.getLuckyBet().setBlue1(0);
        if(isSameSecondPrizeAppeared(context.getLuckyBet())){
            context.setRedBallBingo(false);
        }
    }
}
