package com.fick.luckyfick.tcb.strategy.impl.red.pre;

import com.fick.luckyfick.tcb.strategy.RedBallPreStrategy;
import com.fick.luckyfick.tcb.strategy.TcbStrategyContext;
import com.fick.luckyfick.tcb.strategy.impl.BaseStrategy;

/**
 * @name: RedBallPreStrategyIncludeByAppearLastN
 * @program: luckyfick
 * @description: 红球根据近lastN次出现次数小于指定值，将直接包含
 * @author: figo.song
 * @created: 2020/4/22
 **/
public class RedBallPreStrategyIncludeByAppearLastN extends BaseStrategy implements RedBallPreStrategy {

    /**
     * 近lastN
     */
    private int lastN;

    /**
     * 排除阈值
     */
    private int threshold;

    public RedBallPreStrategyIncludeByAppearLastN(int lastN, int threshold) {
        this.lastN = lastN;
        if(lastN <= 0){
            this.lastN = 10;
        }
        this.threshold = threshold;
        if(threshold <= 0){
            this.threshold = 1;
        }
    }

    @Override
    public void bingo(TcbStrategyContext context) {
        for(int ballNumber = 1 ; ballNumber <= 33 ; ballNumber ++ ){
            int count = historyAnalysisService.getRedBallCountInLast(lastN,ballNumber);
            if(count < threshold && !context.getRedBallIncluded().contains(ballNumber) && !context.getRedBallExcluded().contains(ballNumber)){
                context.getRedBallExcluded().add(ballNumber);
            }
        }
    }
}
