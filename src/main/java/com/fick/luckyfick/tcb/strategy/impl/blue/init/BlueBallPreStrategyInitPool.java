package com.fick.luckyfick.tcb.strategy.impl.blue.init;

import com.fick.luckyfick.tcb.strategy.BlueBallInitStrategy;
import com.fick.luckyfick.tcb.strategy.TcbStrategyContext;
import com.fick.luckyfick.tcb.strategy.impl.BaseStrategy;

import java.util.ArrayList;

/**
 * @name: BlueBallInitPoolStrategy
 * @program: luckyfick
 * @description: 蓝球初始化选池策略
 * @author: figo.song
 * @created: 2020/4/22
 **/
public class BlueBallPreStrategyInitPool extends BaseStrategy implements BlueBallInitStrategy {

    /**
     * 蓝球选池容量，最小lastN * 1 * 2
     */
    private int poolCapacity = 200;

    /**
     * 分析历史数据，lastN，默认100
     */
    private int lastN = 100;

    public BlueBallPreStrategyInitPool(int poolCapacity, int lastN) {
        this.lastN = lastN;
        if(poolCapacity < lastN * 1 * 2){
            this.poolCapacity = lastN * 1 * 2;
        } else {
            this.poolCapacity = poolCapacity;
        }
        // 把容量变成16的倍数，尽量平均
        int remain = this.poolCapacity % 16;
        this.poolCapacity = this.poolCapacity + (16 - remain);
    }

    @Override
    public void bingo(TcbStrategyContext context) {
        // 初始化蓝球选池
        context.setBlueBallPool(new ArrayList<>(poolCapacity));
        for(int i = 0 ; i < poolCapacity ; i ++ ){
            context.getBlueBallPool().add(i,(i % 16) + 1);
        }
        // 将历史中已经出现的次数删除
        int appear = poolCapacity / 16;
        for(int i = 1 ; i <= 16 ; i ++) {
            int count = historyAnalysisService.getBlueBallCountInLast(lastN,i);
            if(count <= 0){
                continue;
            }
            if(count > appear){
                count = appear - 1;
            }
            int[] indexes = new int[count];
            int filledIndex = 0;
            for(int index = 0 ; index < context.getBlueBallPool().size() ; index ++){
                if(context.getBlueBallPool().get(index) == i){
                    indexes[filledIndex ++] = index;
                }
                if(filledIndex == count){
                    break;
                }
            }
            for( int ri = count ; ri >0 ; ri -- ){
                context.getBlueBallPool().remove(indexes[ri - 1]);
            }
        }
        // 将历史中连续缺失次数加进来
        for(int i = 1 ; i <= 16 ; i ++) {
            int missCount = historyAnalysisService.getBlueBallMissCountInLast(i);
            for(int j = 0 ; j < missCount ; j ++){
                context.getBlueBallPool().add(i);
            }
        }
    }
}
