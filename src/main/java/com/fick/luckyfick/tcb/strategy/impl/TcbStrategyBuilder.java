package com.fick.luckyfick.tcb.strategy.impl;

import com.fick.luckyfick.tcb.strategy.TcbStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @name: TcbStrategyBuilder
 * @program: luckyfick
 * @description: 策略builder
 * @author: figo.song
 * @created: 2020/4/22
 **/
public class TcbStrategyBuilder {

    private List<TcbStrategy> strategies = new ArrayList<>();

    public TcbStrategyBuilder add(TcbStrategy strategy){
        strategies.add(strategy);
        return this;
    }

    public List<TcbStrategy> build(){
        return strategies;
    }

}
