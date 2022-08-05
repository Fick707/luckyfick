package com.fick.luckyfick.tcb.strategy.impl.red.pre;

import com.fick.luckyfick.tcb.strategy.RedBallPreStrategy;
import com.fick.luckyfick.tcb.strategy.TcbStrategyContext;
import com.fick.luckyfick.tcb.strategy.impl.BaseStrategy;
import org.apache.commons.collections4.CollectionUtils;

/**
 * @name: RedBallPreStrategyIncludeByAbsence
 * @program: luckyfick
 * @description: 根据最近缺失次数确定必选球
 * @author: figo.song
 * @created: 2020/4/22
 **/
public class RedBallPreStrategyIncludeByAbsence extends BaseStrategy implements RedBallPreStrategy {

    /**
     * 必选阈值
     */
    private int threshold;

    public RedBallPreStrategyIncludeByAbsence(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void bingo(TcbStrategyContext context) {
        for(int ballNumber = 1 ; ballNumber <= 33 ; ballNumber ++ ){
            int absenceCount = historyAnalysisService.getRedBallMissCountInLast(ballNumber);
            if(absenceCount > threshold
                    && !context.getRedBallIncluded().contains(ballNumber)
                    && !context.getRedBallExcluded().contains(ballNumber)){
                context.getRedBallIncluded().add(ballNumber);
            }
        }
    }
}
