package com.fick.luckyfick.tcb.strategy;

import com.fick.luckyfick.service.HistoryAnalysisService;

/**
 * @name: TcbStrategy
 * @program: luckyfick
 * @description: 双色球策略接口定义
 * @author: figo.song
 * @created: 2020/4/22
 **/
public interface TcbStrategy {

    /**
     * 执行策略
     * @param context
     */
    void bingo(TcbStrategyContext context);

    /**
     * 设置历史分析服务
     * @param historyAnalysisService
     */
    void setHistoryAnalysisService(HistoryAnalysisService historyAnalysisService);
}
