package com.fick.luckyfick.service;

import com.fick.luckyfick.model.MyBet;

/**
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @create: 2020/4/22
 **/
public interface MyTcbBetService {

    /**
     * 添加我的投注记录
     * @param myBet
     * @return
     */
    Integer addMyBet(MyBet myBet);

    /**
     * 生成最新一期的我的投注
     */
    void generateMyBet();

    /**
     * 检查最新一期的投注是否中奖
     */
    void checkResult();
}
