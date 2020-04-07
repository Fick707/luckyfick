package com.fick.luckyfick.dao;

import com.fick.luckyfick.model.Bet;

import java.util.List;

/**
 * @program: luckyfick
 * @description: 双色球历史DAO
 * @author: figo.song
 * @create: 2020/4/7
 **/
public interface TwoColorBallHistoryDAO {

    /**
     * 加载所有历史记录
     * @return
     */
    List<Bet> loadHistory();

    /**
     * 添加历史记录
     * @param bet
     */
    void addHistory(Bet bet);

    /**
     * 从历史记录中获取最后一条，用于id自增
     * @return
     */
    Bet getLastBet();

    /**
     * 根据历史记录，生成自增id
     * @return
     */
    Integer generateIndex();
}
