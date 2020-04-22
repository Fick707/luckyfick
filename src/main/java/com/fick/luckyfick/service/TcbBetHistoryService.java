package com.fick.luckyfick.service;

import com.fick.luckyfick.model.Bet;

/**
 * @program: luckyfick
 * @description: 双色球中奖历史服务
 * @author: figo.song
 * @create: 2020/4/22
 **/
public interface TcbBetHistoryService {

    /**
     * 添加中奖历史
     * @param bet
     * @return
     */
    Integer addBetHistory(Bet bet);
}
