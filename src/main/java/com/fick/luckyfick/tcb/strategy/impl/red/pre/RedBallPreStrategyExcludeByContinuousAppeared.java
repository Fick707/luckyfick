package com.fick.luckyfick.tcb.strategy.impl.red.pre;

import com.fick.luckyfick.tcb.strategy.RedBallPreStrategy;
import com.fick.luckyfick.tcb.strategy.TcbStrategyContext;
import com.fick.luckyfick.tcb.strategy.impl.BaseStrategy;

/**
 * @name: RedBallAfterStrategyExcludeByContinuousAppeared
 * @program: luckyfick
 * @description: 根据最近连续出现次数，排除
 * @author: figo.song
 * @created: 2020/4/22
 **/
public class RedBallPreStrategyExcludeByContinuousAppeared extends BaseStrategy implements RedBallPreStrategy {

    private int threshold;

    public RedBallPreStrategyExcludeByContinuousAppeared(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void bingo(TcbStrategyContext context) {
        for(int ballNumber = 1 ; ballNumber <= 33 ; ballNumber ++ ){
            int count = historyAnalysisService.getRedBallContinuousAppearCount(ballNumber);
            if(count >= threshold){
                context.getRedBallExcluded().add(ballNumber);
            }
        }
    }
}
