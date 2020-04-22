package com.fick.luckyfick.common;

import com.fick.luckyfick.BaseTest;
import com.fick.luckyfick.model.MyBet;
import com.fick.luckyfick.service.MyTcbBetHistoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @create: 2020/4/22
 **/
public class MyTcbBetHistoryServiceImplTest extends BaseTest {

    @Autowired
    MyTcbBetHistoryService myTcbBetHistoryService;

    @Test
    public void testAddMyBet(){
        MyBet myBet = betOneMy(2,4,8,12,22,32,15,3);
        myBet.setCode(2020028);
        myBet.setDate(System.currentTimeMillis());
        myTcbBetHistoryService.addMyBet(myBet);
    }
}
