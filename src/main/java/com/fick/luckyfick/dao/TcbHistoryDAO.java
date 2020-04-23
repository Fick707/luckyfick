package com.fick.luckyfick.dao;

import com.fick.luckyfick.model.Bet;

import java.util.List;

/**
 * @program: luckyfick
 * @description: 双色球历史DAO
 * @author: figo.song
 * @create: 2020/4/7
 **/
public interface TcbHistoryDAO {

    /**
     * 加载所有历史记录
     * @return
     */
    List<Bet> loadHistory();

    /**
     * 添加历史记录
     * @param bet
     * @return bet index
     */
    Integer addHistory(Bet bet);

    /**
     * 从官网获取历史中奖号码，只获取最新的
     * @return
     */
    void fetchFromOfficial();

}
