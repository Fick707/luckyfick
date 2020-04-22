package com.fick.luckyfick.service;

import com.fick.luckyfick.model.MyBet;

/**
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @create: 2020/4/22
 **/
public interface MyTcbBetHistoryService {

    /**
     * 添加我的投注记录
     * @param myBet
     * @return
     */
    Integer addMyBet(MyBet myBet);
}
