package com.fick.luckyfick.tcb.strategy.impl.blue.pre;

import com.fick.luckyfick.tcb.strategy.BlueBallPreStrategy;
import com.fick.luckyfick.tcb.strategy.TcbStrategyContext;
import com.fick.luckyfick.tcb.strategy.impl.BaseStrategy;
import org.apache.commons.collections4.CollectionUtils;

/**
 * @name: BlueBallPreStrategyExcludeByAppearLastN
 * @program: luckyfick
 * @description: 蓝球根据近lastN次出现次数排除
 * @author: figo.song
 * @created: 2020/4/22
 **/
public class BlueBallPreStrategyIncludeByAppearLastN extends BaseStrategy implements BlueBallPreStrategy {

    /**
     * 近lastN
     */
    private int lastN;

    /**
     * 排除阈值
     */
    private int threshold;

    public BlueBallPreStrategyIncludeByAppearLastN(int lastN, int threshold) {
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
            int minCount = 0;
            if(CollectionUtils.isNotEmpty(context.getBlueBallExcluded())){
                minCount = context.getBlueBallExcluded().stream().mapToInt(Integer::intValue).min().getAsInt();
            }
            if(count < threshold
                    // 包含列表为空或者缺失次数小于当前最小
                    && (CollectionUtils.isEmpty(context.getBlueBallIncluded()) || count < minCount)
                    // 包含列表里还未包含
                    && !context.getBlueBallIncluded().contains(ballNumber)
                    // 排除列表里未包含
                    && !context.getBlueBallExcluded().contains(ballNumber)){
                context.getBlueBallIncluded().add(ballNumber);
            }
        }
    }
}
