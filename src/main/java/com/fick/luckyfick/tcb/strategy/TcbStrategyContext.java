package com.fick.luckyfick.tcb.strategy;

import com.fick.luckyfick.model.Bet;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @name: TcbStrategyContext
 * @program: luckyfick
 * @description: 双色球策略上下文对象
 * @author: figo.song
 * @created: 2020/4/22
 **/
@Data
@ToString
public class TcbStrategyContext {

    /**
     * 红球随机选池
     */
    private List<Integer> redBallPool = new ArrayList<>();

    /**
     * 排除的红球
     */
    private List<Integer> redBallExcluded = new ArrayList<>();

    /**
     * 必选的红球
     */
    private List<Integer> redBallIncluded = new ArrayList<>();

    /**
     * 蓝球随机选池
     */
    private List<Integer> blueBallPool;

    /**
     * 排除的蓝球
     */
    private List<Integer> blueBallExcluded = new ArrayList<>();

    /**
     * 必选的蓝球
     */
    private List<Integer> blueBallIncluded = new ArrayList<>();

    /**
     * 最终结果
     */
    private Bet luckyBet = new Bet();

    /**
     * 红球是否ok了
     */
    private boolean isRedBallBingo = false;

    /**
     * 蓝球是否ok了
     */
    private boolean isBlueBallBingo = false;

}
