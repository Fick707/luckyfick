package com.fick.luckyfick.tcb.strategy.impl.blue.pre;

import com.fick.luckyfick.tcb.strategy.BlueBallPreStrategy;
import com.fick.luckyfick.tcb.strategy.TcbStrategyContext;
import com.fick.luckyfick.tcb.strategy.impl.BaseStrategy;
import org.apache.commons.collections4.CollectionUtils;

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
            int maxCount = Integer.MAX_VALUE;
            if(CollectionUtils.isNotEmpty(context.getBlueBallIncluded())){
                maxCount = context.getBlueBallIncluded().stream().mapToInt(Integer::intValue).max().getAsInt();
            }
            if(absenceCount > threshold
                    // 包含列表为空或者次数大于当前最大
                    && (CollectionUtils.isEmpty(context.getBlueBallIncluded()) || absenceCount > maxCount)
                    // 包含列表里还未包含
                    && !context.getBlueBallIncluded().contains(ballNumber)
                    // 排除列表里未包含
                    && !context.getBlueBallExcluded().contains(ballNumber)){
                context.getBlueBallIncluded().add(ballNumber);
                break;
            }
        }
    }
}
