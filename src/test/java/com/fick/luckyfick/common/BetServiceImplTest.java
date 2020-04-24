package com.fick.luckyfick.common;

import com.alibaba.fastjson.JSON;
import com.fick.luckyfick.BaseTest;
import com.fick.luckyfick.model.MyBet;
import com.fick.luckyfick.service.BetService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @name: BetServiceImplTest
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @created: 2020/4/22
 **/
public class BetServiceImplTest extends BaseTest {

    @Autowired
    BetService betService;

    @Test
    public void testGetOneLuckyBet(){

        MyBet luckyBet = betService.tripleLucky();
        System.out.println("result:"+ JSON.toJSONString(luckyBet));
    }
}
