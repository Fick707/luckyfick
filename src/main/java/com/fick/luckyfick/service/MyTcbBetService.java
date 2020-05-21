package com.fick.luckyfick.service;

import com.fick.luckyfick.model.MyBet;

import java.util.List;

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

    /**
     * 获取我最后一次投注
     * @return
     */
    List<MyBet> getMyLatestBet();

    /**
     * 获取我在指定期的投注
     * @param code
     * @return
     */
    List<MyBet> getMyBet(Integer code);
}
