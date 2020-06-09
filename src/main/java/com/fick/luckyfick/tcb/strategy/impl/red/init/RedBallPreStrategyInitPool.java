package com.fick.luckyfick.tcb.strategy.impl.red.init;

import com.fick.luckyfick.tcb.strategy.RedBallInitStrategy;
import com.fick.luckyfick.tcb.strategy.TcbStrategyContext;
import com.fick.luckyfick.tcb.strategy.impl.BaseStrategy;

import java.util.ArrayList;

/**
 * @name: RedBallInitPoolStrategy
 * @program: luckyfick
 * @description: 红球初始化选池策略
 * @author: figo.song
 * @created: 2020/4/22
 **/
public class RedBallPreStrategyInitPool extends BaseStrategy implements RedBallInitStrategy {

    /**
     * 红球选池容量，最小lastN * 6 * 2
     */
    private int poolCapacity = 200;

    /**
     * 分析历史数据，lastN，默认100
     */
    private int lastN = 100;

    public RedBallPreStrategyInitPool(int poolCapacity, int lastN) {
        this.lastN = lastN;
        if(lastN > 100){
            this.lastN = 100;
        }
        this.poolCapacity = poolCapacity;
        if(poolCapacity < lastN * 6 * 2){
            this.poolCapacity = lastN * 6 * 2;
        }
        // 把容量变成33的倍数，尽量平均
        int remain = this.poolCapacity % 33;
        this.poolCapacity = this.poolCapacity + (33 - remain);
    }

    @Override
    public void bingo(TcbStrategyContext context) {
        // 初始化红球选池
        context.setRedBallPool(new ArrayList<>(poolCapacity));
        for(int i = 0 ; i < poolCapacity ; i ++ ){
            context.getRedBallPool().add(i,(i % 33) + 1);
        }
        // 将历史中已经出现的次数删除
        int appear = poolCapacity / 33;
        for(int i = 1 ; i <= 33 ; i ++) {
            int count = historyAnalysisService.getRedBallCountInLast(lastN,i);
            if(count <= 0){
                continue;
            }
            if(count > appear){
                count = appear - 1;
            }
            int[] indexes = new int[count];
            int filledIndex = 0;
            for(int index = 0 ; index < context.getRedBallPool().size() ; index ++){
                if(context.getRedBallPool().get(index) == i){
                    indexes[filledIndex ++] = index;
                }
                if(filledIndex == count){
                    break;
                }
            }
            for( int ri = count ; ri >0 ; ri -- ){
                context.getRedBallPool().remove(indexes[ri - 1]);
            }
        }
        // 将历史中连续缺失次数加进来
        for(int i = 1 ; i <= 33 ; i ++) {
            int missCount = historyAnalysisService.getRedBallMissCountInLast(i);
            for(int j = 0 ; j < missCount ; j ++){
                context.getRedBallPool().add(i);
            }
        }
    }
}
