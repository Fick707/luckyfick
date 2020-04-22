package com.fick.luckyfick.tcb.strategy.impl.blue.pre;

import com.fick.luckyfick.tcb.strategy.BlueBallPreStrategy;
import com.fick.luckyfick.tcb.strategy.TcbStrategyContext;
import com.fick.luckyfick.tcb.strategy.impl.BaseStrategy;

/**
 * @name: BlueBallPreStrategyExcludeByContinuousAppeared
 * @program: luckyfick
 * @description: 根据最近连续出现次数，排除
 * @author: figo.song
 * @created: 2020/4/22
 **/
public class BlueBallPreStrategyExcludeByContinuousAppeared extends BaseStrategy implements BlueBallPreStrategy {

    private int threshold;

    public BlueBallPreStrategyExcludeByContinuousAppeared(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void bingo(TcbStrategyContext context) {
        for(int ballNumber = 1 ; ballNumber <= 16 ; ballNumber ++ ){
            int count = historyAnalysisService.getBlueBallContinuousAppearCount(ballNumber);
            if(count > threshold){
                context.getBlueBallExcluded().add(ballNumber);
            }
        }
    }
}
