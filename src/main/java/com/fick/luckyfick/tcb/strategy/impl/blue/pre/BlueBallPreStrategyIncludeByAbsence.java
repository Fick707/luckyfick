package com.fick.luckyfick.tcb.strategy.impl.blue.pre;

import com.fick.luckyfick.tcb.strategy.BlueBallPreStrategy;
import com.fick.luckyfick.tcb.strategy.TcbStrategyContext;
import com.fick.luckyfick.tcb.strategy.impl.BaseStrategy;

/**
 * @name: BlueBallPreStrategyIncludeByAbsence
 * @program: luckyfick
 * @description: 根据最近缺失次数确定必选球
 * @author: figo.song
 * @created: 2020/4/22
 **/
public class BlueBallPreStrategyIncludeByAbsence extends BaseStrategy implements BlueBallPreStrategy {

    /**
     * 必选阈值
     */
    private int threshold;

    public BlueBallPreStrategyIncludeByAbsence(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void bingo(TcbStrategyContext context) {
        for(int ballNumber = 1 ; ballNumber <= 16 ; ballNumber ++ ){
            int absenceCount = historyAnalysisService.getBlueBallMissCountInLast(ballNumber);
            if(absenceCount <= threshold){
                context.getBlueBallIncluded().add(ballNumber);
                break;
            }
        }
    }
}
