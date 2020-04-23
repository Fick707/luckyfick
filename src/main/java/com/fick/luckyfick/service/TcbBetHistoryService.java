package com.fick.luckyfick.service;

/**
 * @program: luckyfick
 * @description: 双色球中奖历史服务
 * @author: figo.song
 * @create: 2020/4/22
 **/
public interface TcbBetHistoryService {

    /**
     * 从官网获取中奖历史并合并到系统中
     */
    void mergeTcbBetHistoryFromOfficial();
}
