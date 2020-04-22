package com.fick.luckyfick.tcb.strategy.impl.blue.after;

import com.fick.luckyfick.tcb.strategy.BlueBallAfterStrategy;
import com.fick.luckyfick.tcb.strategy.TcbStrategyContext;
import com.fick.luckyfick.tcb.strategy.impl.BaseStrategy;

/**
 * @name: BetAfterStrategyExcludeByFirstPrizeAppeared
 * @program: luckyfick
 * @description: 一等奖出现过的，排除，因为蓝球后出，可以在这里判断所有球
 * @author: figo.song
 * @created: 2020/4/22
 **/
public class BlueBallAfterStrategyExcludeByFirstPrizeAppeared extends BaseStrategy implements BlueBallAfterStrategy {

    @Override
    public void bingo(TcbStrategyContext context) {
        if(isSameFirstPrizeAppeared(context.getLuckyBet())){
            context.setBlueBallBingo(false);
        }
    }
}
