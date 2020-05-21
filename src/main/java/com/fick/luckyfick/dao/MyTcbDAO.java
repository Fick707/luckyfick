package com.fick.luckyfick.dao;

import com.fick.luckyfick.model.MyBet;

import java.util.List;

/**
 * @program: luckyfick
 * @description: 我的双色球记录
 * @author: figo.song
 * @create: 2020/4/22
 **/
public interface MyTcbDAO {

    /**
     * 加载所有历史记录
     * @return
     */
    List<MyBet> loadMyHistory();

    /**
     * 添加我的投注记录
     * @param bet
     * @return index
     */
    Integer addMyBet(MyBet bet);

    /**
     *
     * @return
     */
    Integer getMyLastBetCode();
}
