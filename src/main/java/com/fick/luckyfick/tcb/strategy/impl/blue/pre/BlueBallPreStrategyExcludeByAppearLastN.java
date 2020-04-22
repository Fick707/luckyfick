package com.fick.luckyfick.tcb.strategy.impl.blue.pre;

import com.fick.luckyfick.service.HistoryAnalysisService;
import com.fick.luckyfick.tcb.strategy.BlueBallPreStrategy;
import com.fick.luckyfick.tcb.strategy.TcbStrategyContext;
import com.fick.luckyfick.tcb.strategy.impl.BaseStrategy;

/**
 * @name: BlueBallPreStrategyExcludeByAppearLastN
 * @program: luckyfick
 * @description: 蓝球根据近lastN次出现次数排除
 * @author: figo.song
 * @created: 2020/4/22
 **/
public class BlueBallPreStrategyExcludeByAppearLastN extends BaseStrategy implements BlueBallPreStrategy {

    /**
     * 近lastN
     */
    private int lastN;

    /**
     * 排除阈值
     */
    private int threshold;

    public BlueBallPreStrategyExcludeByAppearLastN(int lastN, int threshold) {
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
        for(int ballNumber = 1 ; ballNumber <= 16 ; ballNumber ++ ){
            int count = historyAnalysisService.getBlueBallCountInLast(lastN,ballNumber);
            if(count >= threshold){
                context.getBlueBallExcluded().add(ballNumber);
            }
        }
    }
}
